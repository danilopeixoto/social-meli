package com.meli.social.api.repository;

import com.meli.social.api.model.AccountModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class FollowedRepository {
  @Autowired
  private DatabaseClient databaseClient;

  @Autowired
  private ModelMapper queryModelMapper;

  public Flux<AccountModel> findByAccountId(Integer accountId, Sort sort) {
    String sortString = sort
      .toString()
      .replace(":", "");

    return databaseClient
      .sql(
        "select a.* from account a " +
          "inner join following f on f.follower_id = $1 and f.followed_id = a.id " +
          "order by a." + sortString)
      .bind("$1", accountId)
      .fetch()
      .all()
      .map(results -> queryModelMapper.map(results, AccountModel.class));
  }
}
