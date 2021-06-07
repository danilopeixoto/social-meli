package com.meli.social.api.service;

import com.meli.social.api.model.*;
import com.meli.social.api.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.NoSuchElementException;

@Service
public class PostService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PostRepository repository;

  @Autowired
  private ProductService productService;

  public Mono<Tuple2<PostModel, ProductModel>> packProduct(PostModel post) {
    return Mono
      .just(post)
      .zipWith(this.productService.findById(post.getProductId()));
  }

  public PostResponseModel toResponse(Tuple2<PostModel, ProductModel> models) {
    return TupleUtils
      .<PostModel, ProductModel, PostResponseModel>function((post, product) -> this.modelMapper
        .map(post, PostResponseModel.class)
        .toBuilder()
        .product(this.modelMapper.map(product, ProductDetailResponseModel.class))
        .build())
      .apply(models);
  }

  private Mono<PostRequestModel> createProduct(PostRequestModel postRequest) {
    return Mono
      .justOrEmpty(postRequest.getOptionalProductId())
      .switchIfEmpty(Mono
        .justOrEmpty(postRequest.getOptionalProduct())
        .switchIfEmpty(Mono.error(new IllegalArgumentException("The product ID or product field is required.")))
        .map(product -> this.modelMapper
          .map(product, ProductRequestModel.class)
          .toBuilder()
          .accountId(postRequest.getAccountId())
          .build())
        .flatMap(product -> this.productService.create(Mono.just(product)))
        .map(ProductModel::getId))
      .flatMap(this.productService::findById)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Product resource not found.")))
      .filter(product -> product
        .getAccountId()
        .equals(postRequest.getAccountId()))
      .switchIfEmpty(Mono.error(new IllegalAccessException("Account resource cannot access product.")))
      .map(product -> postRequest
        .toBuilder()
        .productId(product.getId())
        .build());
  }

  @Transactional
  public Mono<PostResponseModel> create(Mono<PostRequestModel> postRequest) {
    return postRequest
      .flatMap(this::createProduct)
      .map(post -> this.modelMapper.map(post, PostModel.class))
      .flatMap(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new DuplicateKeyException("Account resource not found."))
      .flatMap(this::packProduct)
      .map(this::toResponse);
  }

  public Mono<PostResponseModel> findById(Integer id) {
    return this.repository
      .findById(id)
      .flatMap(this::packProduct)
      .map(this::toResponse);
  }

  public Flux<PostResponseModel> findByProductId(Integer productId, Pageable pageable) {
    return this.repository
      .findByProductId(productId)
      .skip(pageable.getOffset())
      .take(pageable.getPageSize())
      .flatMap(this::packProduct)
      .map(this::toResponse);
  }

  public Flux<PostResponseModel> list(Pageable pageable) {
    return this.repository
      .findAll(pageable.getSort())
      .onErrorMap(
        BadSqlGrammarException.class,
        (exception) -> new IllegalArgumentException("Invalid sort parameter."))
      .skip(pageable.getOffset())
      .take(pageable.getPageSize())
      .flatMap(this::packProduct)
      .map(this::toResponse);
  }

  @Transactional
  public Mono<PostResponseModel> update(Integer id, Mono<PostRequestModel> postRequest) {
    return this.repository
      .findById(id)
      .flatMap(post -> postRequest
        .flatMap(this::createProduct)
        .map(postUpdate -> this.modelMapper.map(postUpdate, PostModel.class))
        .map(postUpdate -> postUpdate
          .toBuilder()
          .id(post.getId())
          .createdAt(post.getCreatedAt())
          .build()))
      .flatMap(this.repository::save)
      .onErrorMap(
        DataIntegrityViolationException.class,
        (exception) -> new DuplicateKeyException("Account resource not found."))
      .flatMap(this::packProduct)
      .map(this::toResponse);
  }

  public Mono<PostResponseModel> delete(Integer id) {
    return this.repository
      .findById(id)
      .flatMap(this::packProduct)
      .flatMap(TupleUtils
        .function((post, product) -> this.repository
          .deleteById(post.getId())
          .thenReturn(Tuples.of(post, product))))
      .map(this::toResponse);
  }

  public Mono<Boolean> exists(Integer id) {
    return this.repository.existsById(id);
  }
}
