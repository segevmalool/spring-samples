package com.segbaus;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

  @Value("${PG_USERNAME}")
  private String postgresUsername;

  @Value("${PG_PASSWORD}")
  private String postgresPassword;

  @Value("${PG_DB_NAME}")
  private String postgresDbName;

  @Value("${PG_HOST}")
  private String postgresHost;

  @Value("${PG_PORT}")
  private Integer postgresPort;

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactoryOptions options =
        ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.USER, postgresUsername)
            .option(ConnectionFactoryOptions.PASSWORD, postgresPassword)
            .option(ConnectionFactoryOptions.DATABASE, postgresDbName)
            .option(ConnectionFactoryOptions.HOST, postgresHost)
            .option(ConnectionFactoryOptions.PORT, postgresPort)
            .option(ConnectionFactoryOptions.DRIVER, "postgres")
            .build();

    return ConnectionFactories.get(options);
  }
}
