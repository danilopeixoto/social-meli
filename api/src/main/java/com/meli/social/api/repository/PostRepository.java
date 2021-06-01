package com.meli.social.api.repository;

import com.meli.social.api.model.OrderEnum;
import com.meli.social.api.model.PostModel;
import com.meli.social.api.model.ProductModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveCrudRepository<PostModel, Integer> {
  @Query("")
  Flux<PostModel> findAllSortedByDate(OrderEnum order);
}
