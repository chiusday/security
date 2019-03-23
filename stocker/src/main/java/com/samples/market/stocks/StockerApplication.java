package com.samples.market.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@EnableResourceServer
@SpringBootApplication(exclude=UserDetailsServiceAutoConfiguration.class)
public class StockerApplication {

	public static void main(String[] args){
		SpringApplication.run(StockerApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("security.oauth2.resource")
	public ResourceServerTokenServices tokenServices() {
		return new RemoteTokenServices();
	}
}
