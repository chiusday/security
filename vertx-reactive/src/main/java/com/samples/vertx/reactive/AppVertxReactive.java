package com.samples.vertx.reactive;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import com.samples.vertx.reactive.verticle.DataAccessInterchange;

import io.vertx.core.VertxOptions;
import io.vertx.reactivex.core.Vertx;

@EnableOAuth2Client
@SpringBootApplication(exclude=UserDetailsServiceAutoConfiguration.class)
public class AppVertxReactive {
	@Autowired
	private DataAccessInterchange dataAccessInterchange;

	public static void main(String... args) {
		SpringApplication.run(AppVertxReactive.class, args);
	}
	
	@PostConstruct
	public void deployVerticles() {
		VertxOptions options = new VertxOptions();
		options.setBlockedThreadCheckInterval(1000*60*60);
		final Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle(dataAccessInterchange);
	}
	
	@Bean
	@ConfigurationProperties("security.oauth2.client")
	public ClientCredentialsResourceDetails resourceDetails() {
		return new ClientCredentialsResourceDetails();
	}
	
	@Bean
	public OAuth2RestOperations restTemplate() {
		return new OAuth2RestTemplate
				(resourceDetails(), new DefaultOAuth2ClientContext());
	}
}
