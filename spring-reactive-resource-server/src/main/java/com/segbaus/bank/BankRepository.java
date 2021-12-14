package com.segbaus.bank;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class BankRepository {
  private DatabaseClient client;

  @Autowired
  BankRepository(ConnectionFactory connectionFactory) {
    this.client = DatabaseClient.create(connectionFactory);
  }

  @Transactional
  public Mono<Row> addTransaction(UUID source, UUID target, Double amount) {
    UUID transactionId = UUID.randomUUID();
    return this.client
        .sql(
            "insert into transaction (source, target, amount, id) values (:source, :target, :amount, :transactionId)")
        .bind("source", source)
        .bind("target", target)
        .bind("amount", amount)
        .bind("transactionId", transactionId)
        .map(row -> row)
        .all()
        .next();
  }
}
