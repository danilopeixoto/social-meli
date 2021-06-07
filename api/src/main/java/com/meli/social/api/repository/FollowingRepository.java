package com.meli.social.api.repository;

import com.meli.social.api.model.FollowingModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FollowingRepository extends ReactiveCrudRepository<FollowingModel, Integer> {
  @Query("delete from following where follower_id = :follower_id and followed_id = :followed_id")
  Mono<Void> deleteByIds(@Param("follower_id") Integer followerId, @Param("followed_id") Integer followedId);

  @Query("select count(id) from following where follower_id = :follower_id")
  Mono<Long> countByFollowerId(@Param("follower_id") Integer followerId);

  @Query("select count(id) from following where followed_id = :followed_id")
  Mono<Long> countByFollowedId(@Param("followed_id") Integer followedId);
}
