package com.segbaus.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;

@EnableWebFluxSecurity
public class SecurityConfiguration {

  Log log = LogFactory.getLog(SecurityConfiguration.class);

  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange()
        .pathMatchers("/actuator/**")
        .access(
            (authenticationMono, context) ->
                Mono.just(new AuthorizationDecision(true))
                    .doOnNext((authn) -> this.log.info("hello")))
        .anyExchange()
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .opaqueToken()
        .introspector(new GoogleIntrospector())
        .and()
        .and()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .csrf()
        .disable();
    return http.build();
  }

  private class GoogleIntrospector implements ReactiveOpaqueTokenIntrospector {

    private final String googleTokenInfoEndpoint = "https://oauth2.googleapis.com/tokeninfo";

    private record GoogleIntrospectionResponse(
      String azp,
      String aud,
      String sub,
      String scope,
      String exp,
      String expires_in,
      String email,
      String email_verified,
      String access_type
    ) {}

    private record GoogleIntrospectionError(
      String error,
      String error_description
    ) {}

    public Mono<OAuth2AuthenticatedPrincipal> introspect(String access_token) {
      return WebClient.create(googleTokenInfoEndpoint).get().uri(
          uriBuilder -> uriBuilder.queryParam("access_token", access_token).build()
        )
        .retrieve()
        .onStatus(status -> status.equals(HttpStatus.BAD_REQUEST), clientResponse ->
          Mono.error(new IntrospectionException("Invalid token"))
        )
        .bodyToMono(GoogleIntrospectionResponse.class)
        .doOnNext(googleIntrospectionResponse -> {
          log.info(googleIntrospectionResponse);
        })
        .flatMap(googleIntrospectionResponse -> Mono.just(
          // This should probably have more meaning based on the introspection response.
          new OAuth2IntrospectionAuthenticatedPrincipal(
            Map.of("hello", "world"),
            List.of(new OAuth2UserAuthority(
              Map.of("userAuthority","ROLE_USER")
            ))
          )
        ));
    }
  }
}
