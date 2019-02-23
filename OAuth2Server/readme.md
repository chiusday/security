### Spring OAuth2 Authorization Server:
1. Add spring-security-oauth2 in the pom
2. Annotate with @EnableAuthorizationServer
3. Create a WebSecurity Configuration that extends WebSecurityConfigurerAdapter with the following:
     - An AuthenticationManager Bean
     - A PasswordEncoder
     - Configure the HttpSecurity
     - Configure the Authentication Builder
4. Create a configuration class that extends AuthorizationServerConfigurerAdapter with the following:
     - Configure the AuthorizationServerSecurityConfigurer
       - Set needed access to verify tokens by setting tokenKeyAccess and checkTokenAccess. 
     - Configure the ClientDetailsServiceConfigurer
     - Configure the AuthorizationServerEndpointConfigurer.
       - Set the tokenStore
       - Set authenticationManager (autowire from #3)
     
