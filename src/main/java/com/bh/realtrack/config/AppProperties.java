package com.bh.realtrack.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration("app")
public class AppProperties {

	@Value("${app.oidc-issuer}")
	private String oidcIssuer;

}
