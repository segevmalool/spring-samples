package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

	private final String someBean;

	public DemoApplication(@Autowired(required = false) String someBean) {
		this.someBean = someBean;
	}

	@Value("${my.var}")
	private String myVar;

	@Override
	public void run(String... args) {
		log.info(someBean);
		log.info(log.toString());
		log.info("myvar: " + myVar);

		Mono.just("elephant").flatMap( event1 -> {
			log.info("event1: " + event1);
			return Mono.just("aligator").doOnNext(event2 -> {
				log.info("event2: " + event2);
			});
		}).block();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
