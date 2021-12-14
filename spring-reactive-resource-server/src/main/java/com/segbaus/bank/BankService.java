package com.segbaus.bank;

import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BankService {
  public Mono<UUID> getSourceAccount(Object securityContext) {
    return Mono.just(UUID.fromString("aa9fe0d5-7586-458d-a3de-3e31db995f88"));
  }
}
