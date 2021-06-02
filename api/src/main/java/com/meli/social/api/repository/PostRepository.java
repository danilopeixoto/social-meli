package com.meli.social.api.repository;

import com.meli.social.api.model.OrderEnum;
import com.meli.social.api.model.PostModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveCrudRepository<PostModel, Integer> {
  @Query("select * from post where product_id = :product_id")
  Flux<PostModel> findByProductId(@Param("product_id") Integer productId);

  @Query("select * from post order by created_at :#{#order.shortName()}")
  Flux<PostModel> findAllSortedByCreatedAt(@Param("order") OrderEnum order);
}
