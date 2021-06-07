package com.meli.social.api.service;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.CountResponseModel;
import com.meli.social.api.repository.FollowedRepository;
import com.meli.social.api.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
public class FollowedService {
  @Autowired
  private FollowedRepository repository;

  @Autowired
  private FollowingRepository followingRepository;

  @Autowired
  private AccountService accountService;

  public Flux<AccountModel> list(Integer id, Pageable pageable) {
    return Flux
      .just(id)
      .filterWhen(this.accountService::exists)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account resource not found.")))
      .flatMap(accountId -> this.repository.findByAccountId(accountId, pageable.getSort()))
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize());
  }

  public Mono<CountResponseModel> count(Integer id) {
    return Mono
      .just(id)
      .filterWhen(this.accountService::exists)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account resource not found.")))
      .flatMap(this.followingRepository::countByFollowerId)
      .map(count -> CountResponseModel
        .builder()
        .count(count.intValue())
        .build());
  }
}
