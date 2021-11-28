package com.segbaus.user;

import com.segbaus.config.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@WebFluxTest
@ContextConfiguration(classes = {UserController.class, SecurityConfiguration.class})
public class userTest {
  @Autowired
  ApplicationContext context;

  WebTestClient client;

  @BeforeEach
  public void setClient() {
    this.client = WebTestClient.bindToApplicationContext(context).apply(springSecurity()).build();
  }

  @Test
  public void getUserTestUnAuthN() {
    client.get().uri("/user").exchange()
      .expectStatus().is3xxRedirection()
      .expectHeader().location("/oauth2/authorization/google");
  }

  @Test
  @WithMockUser
  public void getUserTestAuthN() {
    client.get().uri("/user").exchange()
      .expectStatus().is2xxSuccessful()
      .expectBody().jsonPath("$.authentication");
  }
}
