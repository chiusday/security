package com.samples.vertx.reactive;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			//will restrict this when Auth_Code is used
			.authorizeRequests().antMatchers("/").permitAll();
	}
}
