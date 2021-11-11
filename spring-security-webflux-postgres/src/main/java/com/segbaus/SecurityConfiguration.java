package com.segbaus;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange().anyExchange().authenticated().and().httpBasic();
    return http.build();
  }

  @Bean
  MapReactiveUserDetailsService userDetailsService() {
    UserDetails seg =
        User.withDefaultPasswordEncoder().username("seg").password("baus").roles("user").build();
    return new MapReactiveUserDetailsService(seg);
  }
}
