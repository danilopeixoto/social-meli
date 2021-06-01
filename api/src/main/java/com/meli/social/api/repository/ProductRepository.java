package com.meli.social.api.repository;

import com.meli.social.api.model.ProductModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductModel, Integer> {
}
