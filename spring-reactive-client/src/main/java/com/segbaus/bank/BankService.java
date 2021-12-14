package com.segbaus.bank;

import java.util.UUID;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

@Service
public class BankService {
  public UUID getSourceAccount(SecurityContext securityContext) {
    return UUID.fromString("aa9fe0d5-7586-458d-a3de-3e31db995f88");
  }

  public UUID getTargetAccount(SecurityContext securityContext) {
    return UUID.fromString("84178ac4-cba9-4908-abe8-d99683ada850");
  }
}
