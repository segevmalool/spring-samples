package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MyConfig {
  @Bean
  @Profile("local")
  public String someBean() {
    return "I LOVE CONDITIONAL BEANS";
  }
}
