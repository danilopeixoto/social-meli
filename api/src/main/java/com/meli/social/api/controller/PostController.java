package com.meli.social.api.controller;

import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.model.PostRequestModel;
import com.meli.social.api.model.PostResponseModel;
import com.meli.social.api.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Tag(name = "Post service")
@Validated
@RequestMapping("/posts")
@RestController
public class PostController {
  @Autowired
  private PostService service;

  @Operation(summary = "Create new post", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = PostResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @PostMapping
  public Mono<PostResponseModel> create(@Valid @RequestBody Mono<PostRequestModel> postRequest) {
    return this.service.create(postRequest);
  }

  @Operation(summary = "Find post by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = PostResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @GetMapping("/{id}")
  public Mono<PostResponseModel> findById(@Valid @PathVariable Integer id) {
    return this.service
      .findById(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }

  @Operation(summary = "List or find post by product ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = PostResponseModel.class)),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @GetMapping("/")
  public Flux<PostResponseModel> listOrFindByProductId(
    @Valid @RequestParam(value = "product_id", required = false) Integer productId) {
    return Mono
      .justOrEmpty(productId)
      .flux()
      .switchIfEmpty(Mono.error(new IllegalArgumentException()))
      .flatMap(this.service::findByProductId)
      .onErrorResume(
        IllegalArgumentException.class,
        exception -> this.service.list());
  }

  @Operation(summary = "Update post by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = PostResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @PutMapping("/{id}")
  public Mono<PostResponseModel> update(
    @Valid @PathVariable Integer id,
    @Valid @RequestBody Mono<PostRequestModel> postRequest) {
    return this.service
      .update(id, postRequest)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }

  @Operation(summary = "Delete post by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = PostResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "400",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "404",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json")),
    @ApiResponse(
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @DeleteMapping("/{id}")
  public Mono<PostResponseModel> delete(@Valid @PathVariable Integer id) {
    return this.service
      .delete(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }
}