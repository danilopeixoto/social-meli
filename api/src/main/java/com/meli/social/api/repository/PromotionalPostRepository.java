package com.meli.social.api.repository;

import com.meli.social.api.model.PostModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class PromotionalPostRepository {
  @Autowired
  private DatabaseClient databaseClient;

  @Autowired
  private ModelMapper queryModelMapper;

  public Flux<PostModel> findByAccountId(Integer accountId, Sort sort) {
    String sortString = sort
      .toString()
      .replace(":", "");

    return databaseClient
      .sql("select * from post where account_id = $1 and promotional order by " + sortString)
      .bind("$1", accountId)
      .fetch()
      .all()
      .map(results -> queryModelMapper.map(results, PostModel.class));
  }
}
