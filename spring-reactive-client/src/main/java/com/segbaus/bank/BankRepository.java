package com.segbaus.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.UUID;

@Repository
public class BankRepository {
  private WebClient bankClient;

  private Log log = LogFactory.getLog(BankRepository.class);

  @Autowired
  BankRepository(WebClient bankClient) throws SSLException {
    this.bankClient = bankClient;
  }

  public Mono<String> requestTransaction(
    UUID targetAccount,
    Double amount
  ) {
    return this.bankClient
      .post()
      .uri("/transaction")
      .body(BodyInserters.fromValue(new BankController.TransactionRequest(targetAccount, amount)))
      .httpRequest(requestConsumer -> {
        this.log.info(requestConsumer.getHeaders());
      })
      .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
  }
}
