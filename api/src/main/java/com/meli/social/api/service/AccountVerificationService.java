package com.meli.social.api.service;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
public class AccountVerificationService {
  @Autowired
  private AccountRepository repository;

  public Mono<AccountModel> verify(Integer id, Boolean verified) {
    return this.repository
      .findById(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account resource not found")))
      .filter(account -> account.getVerified() != verified)
      .switchIfEmpty(Mono.error(new IllegalStateException("Account verification action already applied.")))
      .map(account -> account
        .toBuilder()
        .verified(verified)
        .build())
      .flatMap(this.repository::save);
  }
}
