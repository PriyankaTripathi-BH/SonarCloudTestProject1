package com.bh.realtrack.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bh.realtrack.config.AppProperties;
import com.bh.realtrack.config.DefaultTrustManager;
import com.bh.realtrack.dto.User;
import com.bh.realtrack.exception.UnknowAuthenticationException;
import com.bh.realtrack.util.AssertUtils;

@Component
public class AbacomFilter extends OncePerRequestFilter {

	private static final String PUBLIC_KEY_URL = "https://public-keys.auth.elb.us-east-1.amazonaws.com/";
	private static final String PUBLIC_KEY_HOSTNAME = "public-keys.auth.elb.us-east-1.amazonaws.com";
	private static String JWT_ISSUER;

	@Autowired
	private AppProperties appProperties;

	private static Logger logger = LoggerFactory.getLogger(AbacomFilter.class.getName());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		JWT_ISSUER = appProperties.getOidcIssuer();
		String path = request.getRequestURI();
		String requestMethod = request.getMethod();
		if (path.equals("/abacom/")) {
			chain.doFilter(request, response);
			return;
		}

		if (requestMethod.equals("OPTIONS")) {
			chain.doFilter(request, response);
			return;
		}

		final String jwtToken = request.getHeader("rt-id-token");
		final String oidcHeaderToken = AssertUtils.sanitizeToken(request.getHeader("x-amzn-oidc-data"));

		if (jwtToken != null) {
			validateToken(request, jwtToken);
			chain.doFilter(request, response);
		} else if (oidcHeaderToken != null) {
			try {
				String[] tokens = StringUtils.split(oidcHeaderToken, ".");

				if (tokens.length < 3) {
					logger.error("Invalid JWT token passed. User cannot be authenticated");

				} else {

					byte[] decodedjwtHeader = Base64.getDecoder().decode(tokens[0]);
					String headerToString = new String(decodedjwtHeader);
					JSONParser parser = new JSONParser();
					JSONObject claimsJson;
					try {
						claimsJson = (JSONObject) parser.parse(headerToString);
						if (claimsJson.containsKey("kid")) {
							String kid = (String) claimsJson.get("kid");
							SSLContext ctx = SSLContext.getInstance("TLS");
							try {
								ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() },
										new SecureRandom());

							} catch (KeyManagementException e1) {
								e1.printStackTrace();
							}
							SSLContext.setDefault(ctx);
							HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

							HostnameVerifier allHostsValid = new HostnameVerifier() {

								@Override
								public boolean verify(String hostname, SSLSession session) {
									return hostname.equals(PUBLIC_KEY_HOSTNAME);
								}
							};
							HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

							URL urlPublicKey = new URL(PUBLIC_KEY_URL + kid);
							HttpsURLConnection connection = (HttpsURLConnection) urlPublicKey.openConnection();

							try {

								BufferedReader br = new BufferedReader(
										new InputStreamReader(connection.getInputStream()));
								String input;
								String publicKey = null;

								while ((input = br.readLine()) != null) {
									if (publicKey != null) {
										publicKey = publicKey + input;
									} else {
										publicKey = input;
									}
								}
								br.close();

								Algorithm algorithm = Algorithm.ECDSA256(getPublicKeyFromString(publicKey), null);
								try {
									JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();

									DecodedJWT decodedJwtToken = verifier.verify(oidcHeaderToken);
									 //String userSSO = decodedJwtToken.getSubject();
									String userSSO = decodedJwtToken.getClaim("subLogin").asString();

									if (userSSO != null
											&& SecurityContextHolder.getContext().getAuthentication() == null) {
										User user = new User();
										user.setUserSSO(userSSO);
										UserDetails userDetails = user;
										UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
												userDetails, null, userDetails.getAuthorities());
										usernamePasswordAuthenticationToken
												.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

										SecurityContextHolder.getContext()
												.setAuthentication(usernamePasswordAuthenticationToken);
									}
								} catch (TokenExpiredException ex) {
									logger.error("This token is already expired. Please refresh and send token", ex);
									ex.printStackTrace();
									response.sendError(response.SC_UNAUTHORIZED,
											"The JWT token has Expired. Please refresh token");
									return;
								} catch (JWTVerificationException ex) {
									logger.error("Error in validating JWT token. User cannot be authenticated", ex);
									ex.printStackTrace();
									response.sendError(response.SC_UNAUTHORIZED,
											"The JWT token is invalid. User cannot be authenticated");
									return;
								}

							} catch (IOException e) {
								logger.error("Error in reading public key from AWS public key url.", e);
								e.printStackTrace();
								response.sendError(response.SC_UNAUTHORIZED,
										"Error in decoding JWT token. User cannot be authenticated");
								return;

							} catch (GeneralSecurityException e) {
								logger.error(
										"Error while converting public key from String to ECPublicKey. Token cannot be verified",
										e);
								e.printStackTrace();
								response.sendError(response.SC_UNAUTHORIZED,
										"Error in decoding JWT token. User cannot be authenticated");
								return;
							}

						}
					} catch (IllegalArgumentException ex) {
						logger.error(" Unable to create alogorithm. Invalid public key", ex);
						ex.printStackTrace();
						response.sendError(response.SC_UNAUTHORIZED, "Authentication failed for user");
						return;

					} catch (ParseException e) {
						logger.error("Error in parsing Header payload of JWT token to JSON. Could not read payload", e);
						e.printStackTrace();
						response.sendError(response.SC_UNAUTHORIZED,
								"Error in parsing JWT token. User cannot be authenticated");
						return;

					} catch (NoSuchAlgorithmException e) {
						logger.error(
								"Error in creating SSLContext for selected algorithm. Could not get public key from AWS",
								e);
						e.printStackTrace();
						response.sendError(response.SC_UNAUTHORIZED, "Authentication failed for user");
						return;
					}

				}
			} catch (IllegalArgumentException e) {
				logger.error("Invalid JWT Token passed.Unable to decode header payload. User Authentication failed");
				e.printStackTrace();
				response.sendError(response.SC_UNAUTHORIZED,
						"Error in parsing JWT token. User cannot be authenticated");
				return;

			}

			chain.doFilter(request, response);
		} else {
			logger.error("Oidc header cannot be empty. Invalid Request format");
			response.sendError(response.SC_UNAUTHORIZED,
					"Invalid Request. Oidc Header is empty.Authentication failed for user");
			return;
		}
	}

	public static ECPublicKey getPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
		String publicKeyPEM = key;
		publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
		byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
		KeyFactory kf = KeyFactory.getInstance("EC");
		ECPublicKey pubKey = (ECPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
		return pubKey;
	}

	public static void validateToken(HttpServletRequest request, String jwtToken) {
		String userSSO = null;

		if (jwtToken == null || jwtToken.isEmpty()) {
			logger.error("Token header cannot be empty.User authentication failed", jwtToken);
			throw new UnknowAuthenticationException("Token header cannot be empty.User authentication failed");
		} else {
			String[] tokens = StringUtils.split(jwtToken, ".");

			if (tokens.length < 3) {
				logger.error("Invalid JWT token passed. User cannot be authenticated");
				throw new UnknowAuthenticationException("Invalid JWT token passed. User cannot be authenticated");
			}

			try {

				byte[] payload = Base64.getDecoder().decode(tokens[1]);
				String payLoadToString = new String(payload);
				JSONParser parser = new JSONParser();
				JSONObject claimsJson;
				try {
					claimsJson = (JSONObject) parser.parse(payLoadToString);
					if (claimsJson.containsKey("sub")) {
						userSSO = (String) claimsJson.get("sub");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (userSSO != null && !userSSO.isEmpty()) {
					User user = new User();
					user.setUserSSO(userSSO);
					UserDetails userDetails = user;
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				} else {
					logger.error("Cannot Parse User Claims from token. User authentication failed");
					throw new UnknowAuthenticationException(
							"Cannot Parse User Claims from token. User authentication failed");
				}

			} catch (IllegalArgumentException e) {
				logger.error("Exception while reading token. User authentication failed", e.getMessage());
				throw new UnknowAuthenticationException("Exception while reading token. User authentication failed");
			}
		}

	}
}
