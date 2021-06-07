package com.meli.social.api.service;

import com.meli.social.api.model.PostResponseModel;
import com.meli.social.api.repository.RecentPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Service
public class RecentPostService {
  @Autowired
  private RecentPostRepository recentPostRepository;

  @Autowired
  private PostService postService;

  public Flux<PostResponseModel> listRecentFollowed(Integer accountId, Instant fromDate, Pageable pageable) {
    return this.recentPostRepository
      .findByFollowerId(accountId, fromDate, pageable.getSort())
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize())
      .flatMap(this.postService::packProduct)
      .map(this.postService::toResponse);
  }
}
