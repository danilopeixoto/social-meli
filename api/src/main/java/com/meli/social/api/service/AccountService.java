package com.meli.social.api.service;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.AccountRequestModel;
import com.meli.social.api.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AccountRepository repository;

  public Mono<AccountModel> create(Mono<AccountRequestModel> accountRequest) {
    return accountRequest
      .map(account -> this.modelMapper
        .map(account, AccountModel.class)
        .toBuilder()
        .verified(false)
        .build())
      .flatMap(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new DuplicateKeyException("Username already exists."));
  }

  public Mono<AccountModel> findById(Integer id) {
    return this.repository.findById(id);
  }

  public Mono<AccountModel> findByUsername(String username) {
    return this.repository.findByUsername(username);
  }

  public Flux<AccountModel> list(Pageable pageable) {
    return this.repository
      .findAll(pageable.getSort())
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize());
  }

  public Mono<AccountModel> update(Integer id, Mono<AccountRequestModel> accountRequest) {
    return this.repository
      .findById(id)
      .flatMap(account -> accountRequest
        .map(accountUpdate -> account
          .toBuilder()
          .username(accountUpdate.getUsername())
          .build()))
      .flatMap(this.repository::save);
  }

  public Mono<AccountModel> delete(Integer id) {
    return this.repository
      .findById(id)
      .flatMap(account -> this.repository
        .deleteById(account.getId())
        .thenReturn(account));
  }

  public Mono<Boolean> exists(Integer id) {
    return this.repository.existsById(id);
  }
}
