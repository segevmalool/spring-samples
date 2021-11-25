package com.segbaus.bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

  //  @PostMapping(value = "/transaction/{amount}")
  //  public Flux<Row> addTransaction(@PathVariable Double amount) {
  //    return ReactiveSecurityContextHolder.getContext().then(securityContextSignal ->
  //    UUID source = bankService.getSourceAccount(securityContext);
  //    UUID target = bankService.getTargetAccount(securityContext);
  //
  //    return this.bankRepo.addTransaction(source, target, amount);
  //  }

  private class Account {}

  @PostMapping(value = "/register")
  public Mono<SecurityContext> regsterAccount() {
    return ReactiveSecurityContextHolder.getContext()
        .doOnSuccess((securityContext) -> log.info(securityContext.getAuthentication()));
  }
}
