package com.meli.social.api.repository;

import com.meli.social.api.model.ProductModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveSortingRepository<ProductModel, Integer> {
  @Query("select * from product where name like concat('%', :name, '%') order by name")
  Flux<ProductModel> findByName(@Param("name") String name);
}
