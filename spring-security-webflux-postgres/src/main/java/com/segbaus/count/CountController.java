package com.segbaus.count;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CountController {
  @GetMapping(value = "/count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Integer> count() {
    Integer n = 10;
    return Flux.range(0, n);
  }
}
