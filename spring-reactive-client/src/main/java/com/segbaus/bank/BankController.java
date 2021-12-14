package com.segbaus.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class BankController {
  private BankRepository bankRepo;
  private BankService bankService;
  private Log log = LogFactory.getLog(this.getClass());

  @Autowired
  BankController(BankRepository bankRepo, BankService bankService) {
    this.bankRepo = bankRepo;
    this.bankService = bankService;
  }

  public record TransactionRequest(UUID targetAccount, Double amount) {}

  @PostMapping(value = "/transaction")
  public Mono<String> addTransaction(@RequestBody TransactionRequest transaction) {
    return bankRepo.requestTransaction(transaction.targetAccount, transaction.amount);
  }

  @PostMapping(value = "/register")
  public Mono<SecurityContext> regsterAccount() {
    return ReactiveSecurityContextHolder.getContext()
        .doOnSuccess((securityContext) -> log.info(securityContext.getAuthentication()));
  }
}
