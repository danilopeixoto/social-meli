package com.meli.social.api.repository;

import com.meli.social.api.model.PostModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ReactiveCrudRepository<PostModel, Integer> {
}
