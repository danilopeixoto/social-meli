package com.meli.social.api.repository;

import com.meli.social.api.model.AccountModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveSortingRepository<AccountModel, Integer> {
  @Query("select * from account where username = :username")
  Mono<AccountModel> findByUsername(@Param("username") String username);
}
