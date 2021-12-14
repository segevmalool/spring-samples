package com.segbaus;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@SpringBootApplication
public class SegbausApplication {

  public static void main(String[] args) {
    SpringApplication.run(SegbausApplication.class, args);
  }

  @Bean
  public WebClient webClient(
    ReactiveClientRegistrationRepository clientRegistrationRepository,
    ServerOAuth2AuthorizedClientRepository authorizedClientRepository
  ) throws SSLException {
    SslContext ssl = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    HttpClient bankHttpClient = HttpClient.create().secure(t -> t.sslContext(ssl));

    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
      new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);
    oauth.setDefaultClientRegistrationId("google");
    oauth.setDefaultOAuth2AuthorizedClient(true);

    return WebClient
      .builder()
      .baseUrl("https://localhost:8443")
      .clientConnector(new ReactorClientHttpConnector(bankHttpClient))
      .filter(oauth)
      .build();
  }
}
