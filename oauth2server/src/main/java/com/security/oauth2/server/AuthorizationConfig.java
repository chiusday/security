package com.security.oauth2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.inMemory()
			.withClient("SampleClientId").secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("client_credentials").authorities("ROLE_CLIENT")
			.scopes("read").autoApprove(true)
			.and()
			.withClient("ResourceSecurityUtilities").secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("client_credentials").authorities("ROLE_CLIENT")
			.scopes("read").autoApprove(true)
			.and()
			.withClient("stockerClientId").secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("client_credentials").authorities("ROLE_CLIENT")
			.scopes("read").autoApprove(true);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {
		TokenStore tokenStore = new InMemoryTokenStore();
		endpoint.tokenStore(tokenStore).authenticationManager(authenticationManager);
	}
}
