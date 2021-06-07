package com.meli.social.api.service;

import com.meli.social.api.model.ProductModel;
import com.meli.social.api.model.ProductRequestModel;
import com.meli.social.api.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
public class ProductService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ProductRepository repository;

  @Autowired
  private AccountService accountService;

  public Mono<ProductModel> create(Mono<ProductRequestModel> productRequest) {
    return productRequest
      .map(product -> this.modelMapper.map(product, ProductModel.class))
      .flatMap(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new NoSuchElementException("Account resource not found."));
  }

  public Mono<ProductModel> findById(Integer id) {
    return this.repository.findById(id);
  }

  public Flux<ProductModel> findByName(String name, Pageable pageable) {
    return this.repository
      .findByName(name)
      .skip(pageable.getOffset())
      .take(pageable.getPageSize());
  }

  public Flux<ProductModel> list(Pageable pageable) {
    return this.repository
      .findAll(pageable.getSort())
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize());
  }

  public Mono<ProductModel> update(Integer id, Mono<ProductRequestModel> productRequest) {
    return this.repository
      .findById(id)
      .flatMap(product -> productRequest
        .map(productUpdate -> this.modelMapper.map(productUpdate, ProductModel.class))
        .map(productUpdate -> productUpdate
          .toBuilder()
          .id(product.getId())
          .build()))
      .flatMap(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new NoSuchElementException("Account resource not found."));
  }

  public Mono<ProductModel> delete(Integer id) {
    return this.repository
      .findById(id)
      .flatMap(account -> this.repository
        .deleteById(account.getId())
        .thenReturn(account))
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new IllegalStateException("Product attached to post."));
  }

  public Mono<Boolean> exists(Integer id) {
    return this.repository.existsById(id);
  }
}
