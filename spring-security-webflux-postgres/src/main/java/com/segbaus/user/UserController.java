package com.segbaus.user;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
  @GetMapping("/user")
  public Mono<SecurityContext> getUser() {
    return ReactiveSecurityContextHolder.getContext();
  }
}
