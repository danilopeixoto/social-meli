package com.meli.social.api.repository;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.OrderEnum;
import com.meli.social.api.model.PostModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountModel, Integer> {
  @Query("")
  Flux<PostModel> findAllSortedByName(OrderEnum order);
}
