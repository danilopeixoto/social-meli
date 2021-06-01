package com.meli.social.api.repository;

import com.meli.social.api.model.AccountModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends ReactiveCrudRepository<FollowerRepository, Integer> { }
