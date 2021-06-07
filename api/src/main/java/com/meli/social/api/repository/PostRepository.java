package com.meli.social.api.repository;

import com.meli.social.api.model.PostModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveSortingRepository<PostModel, Integer> {
  @Query("select * from post where product_id = :product_id order by product_id")
  Flux<PostModel> findByProductId(@Param("product_id") Integer productId);

  @Query("select count(id) from post where account_id = :account_id and promotional")
  Mono<Long> countByAccountIdAndPromotional(@Param("account_id") Integer accountId);
}
