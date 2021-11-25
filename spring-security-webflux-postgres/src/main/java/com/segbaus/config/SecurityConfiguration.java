package com.segbaus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfiguration {
  @Value("${CLIENT_ID}")
  private String clientId;

  @Value("${CLIENT_SECRET}")
  private String clientSecret;

  @Value("${AUTH_URI}")
  private String authUri;

  @Value("${TOKEN_URI}")
  private String tokenUri;

  @Value("${USERINFO_URI}")
  private String userInfoUri;

  @Value("${REDIRECT_URI}")
  private String redirectUri;

  @Value("${JWKS_URI}")
  private String jwksUri;

  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    http
      .authorizeExchange()
      .pathMatchers("/actuator/health").permitAll()
      .anyExchange().authenticated().and()
      .oauth2Client()
      .and()
      .oauth2Login()
      .and()
      .httpBasic()
      .disable()
      .formLogin()
      .disable()
      .csrf()
      .disable();
    return http.build();
  }

  @Bean
  ReactiveClientRegistrationRepository getClientRegistrationRepository() {
    ClientRegistration google =
        ClientRegistration.withRegistrationId("google")
            .scope("openid", "profile", "email")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationUri(authUri)
            .tokenUri(tokenUri)
            .userInfoUri(userInfoUri)
            .redirectUri(redirectUri)
            .jwkSetUri(jwksUri)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .userNameAttributeName("name")
            .build();

    return new InMemoryReactiveClientRegistrationRepository(google);
  }
}
