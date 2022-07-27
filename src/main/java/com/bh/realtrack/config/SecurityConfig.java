package com.bh.realtrack.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bh.realtrack.filter.AbacomFilter;
import com.ge.predix.uaa.token.lib.FastTokenServices;
import com.ge.predix.uaa.token.lib.FastTokenServicesCreator;

/**
 * @author Ganesh Mali
 */
@Configuration
public class SecurityConfig {

    public SecurityConfig() {
        //making sonar happy
    }

    @Configuration
    @Profile("secure")
    @EnableResourceServer
    @EnableWebSecurity
    static class CloudConfig extends ResourceServerConfigurerAdapter {

        @Value("${security.uaa.uri}/oauth/token")
        private String trustedUrl;

        @Value("${security.serviceScope}")
        private String serviceScope;

        @Value("${security.apiScope}")
        private String apiScope;

        @Value("${security.adminScope}")
        private String adminScope;


        @Value("${security.maxSkewSeconds}")
        private int maxSkewSeconds;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId("rt-bhge")
                    .stateless(true)
                    .tokenServices(setFastTokenBean());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .requestMatchers().antMatchers("/**").and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/**").access("#oauth2.hasScope('" + apiScope + "')");

                            }

        @Bean
        public FastTokenServices setFastTokenBean() {
            FastTokenServices tokenService = new FastTokenServicesCreator().newInstance();
            tokenService.setUseHttps(true);
            tokenService.setStoreClaims(true);
            List<String> uaaList = new ArrayList<>();
            // Trusted UUA Url so we can validate token
            uaaList.add(trustedUrl);
            tokenService.setTrustedIssuers(uaaList);
            tokenService.setMaxAcceptableClockSkewSeconds(maxSkewSeconds);
            return tokenService;
        }

    }

    @Configuration
    @Profile("!secure")
    @EnableResourceServer
    @EnableWebSecurity
    static class QaConfig extends ResourceServerConfigurerAdapter {
    	@Autowired
    	AbacomFilter operationsFilter;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/**").permitAll();

            http.addFilterBefore(operationsFilter, UsernamePasswordAuthenticationFilter.class);
        }


    }
}
