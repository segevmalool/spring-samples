package com.segbaus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

@SpringBootApplication
public class SegbausApplication {

  public static void main(String[] args) {
    SpringApplication.run(SegbausApplication.class, args);
  }
}
