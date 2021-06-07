package com.meli.social.api.service;

import com.meli.social.api.model.CountResponseModel;
import com.meli.social.api.model.PostResponseModel;
import com.meli.social.api.repository.AccountRepository;
import com.meli.social.api.repository.PostRepository;
import com.meli.social.api.repository.PromotionalPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
public class PromotionalPostService {
  @Autowired
  private PromotionalPostRepository repository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PostService postService;

  public Flux<PostResponseModel> listByAccount(Integer accountId, Pageable pageable) {
    return Flux
      .just(accountId)
      .filterWhen(this.accountRepository::existsById)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account resource not found.")))
      .flatMap(id -> this.repository.findByAccountId(id, pageable.getSort()))
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize())
      .flatMap(this.postService::packProduct)
      .map(this.postService::toResponse);
  }

  public Mono<CountResponseModel> countByAccount(Integer accountId) {
    return Mono
      .just(accountId)
      .filterWhen(this.accountRepository::existsById)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Account resource not found.")))
      .flatMap(this.postRepository::countByAccountIdAndPromotional)
      .map(count -> CountResponseModel
        .builder()
        .count(count.intValue())
        .build());
  }
}
