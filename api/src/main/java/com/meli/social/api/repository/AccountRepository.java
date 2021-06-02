package com.meli.social.api.repository;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.OrderEnum;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountModel, Integer> {
  @Query("select * from account where username = :username")
  Mono<AccountModel> findByUsername(@Param("username") String username);

  @Query("select * from account order by username :#{#order.shortName()}")
  Flux<AccountModel> findAllSortedByUsername(@Param("order") OrderEnum order);
}
