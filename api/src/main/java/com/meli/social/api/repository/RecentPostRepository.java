package com.meli.social.api.repository;

import com.meli.social.api.model.PostModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Repository
public class RecentPostRepository {
  @Autowired
  private DatabaseClient databaseClient;

  @Autowired
  private ModelMapper queryModelMapper;

  public Flux<PostModel> findByFollowerId(Integer followerId, Instant fromDate, Sort sort) {
    String sortString = sort
      .toString()
      .replace(":", "");

    return databaseClient
      .sql(
        "select p.* from post p " +
          "inner join following f on f.follower_id = $1 and f.followed_id = p.account_id " +
          "where p.created_at >= $2" +
          "order by p." + sortString)
      .bind("$1", followerId)
      .bind("$2", fromDate)
      .fetch()
      .all()
      .map(results -> queryModelMapper.map(results, PostModel.class));
  }
}
