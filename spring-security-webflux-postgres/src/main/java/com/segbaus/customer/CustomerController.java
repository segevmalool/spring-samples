package com.segbaus.customer;

import io.r2dbc.spi.ConnectionFactory;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Component
@RestController
public class CustomerController {

  private DatabaseClient client;

  CustomerController(@Autowired ConnectionFactory connectionFactory) {
    this.client = DatabaseClient.create(connectionFactory);
  }

  @GetMapping("/customers")
  public Flux<UUID> save() {
    return client.sql("select * from customer;").map(row -> (UUID) row.get("id")).all();
  }
  ;
}
