package com.meli.social.api.service;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.FollowingModel;
import com.meli.social.api.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class FollowingService {
  @Autowired
  private FollowingRepository repository;

  @Autowired
  private AccountService accountService;

  public Mono<AccountModel> follow(Integer id, Integer followedId) {
    return Mono
      .just(Tuples.of(id, followedId))
      .filter(results -> !results.getT1().equals(results.getT2()))
      .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot follow your account.")))
      .flatMap(results -> this.repository
        .save(
          FollowingModel
            .builder()
            .followerId(results.getT1())
            .followedId(results.getT2())
            .build()))
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception -> Objects
          .requireNonNull(exception.getMessage())
          .contains("following_unique_constraint") ?
          new IllegalStateException("Account already followed.") :
          new NoSuchElementException("Account resource not found.")))
      .flatMap(following -> accountService.findById(following.getFollowedId()));
  }

  public Mono<AccountModel> unfollow(Integer id, Integer followedId) {
    return this.repository
      .deleteByIds(id, followedId)
      .then(this.accountService.findById(followedId))
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account already unfollowed.")));
  }
}
