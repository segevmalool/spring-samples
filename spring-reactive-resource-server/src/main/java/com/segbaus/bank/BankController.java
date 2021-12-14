package com.segbaus.bank;

import io.r2dbc.spi.Row;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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

  private record TransactionRequest(UUID targetAccount, Double amount) {}

  @PostMapping(value = "/transaction")
  public Mono<Row> addTransaction(@RequestBody TransactionRequest transaction) {
    return ReactiveSecurityContextHolder.getContext()
        .flatMap(
            (SecurityContext securityContext) ->
                Mono.zip(
                        this.bankService.getSourceAccount(securityContext),
                        Mono.just(transaction.targetAccount))
                    .flatMap(
                        (Tuple2<UUID, UUID> sourceTargetTuple) -> {
                          UUID source = sourceTargetTuple.getT1();
                          UUID target = sourceTargetTuple.getT2();

                          return this.bankRepo.addTransaction(source, target, transaction.amount);
                        }));
  }
}
