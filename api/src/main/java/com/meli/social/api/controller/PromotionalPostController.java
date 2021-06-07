package com.meli.social.api.controller;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.CountResponseModel;
import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.model.PostResponseModel;
import com.meli.social.api.service.PromotionalPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Tag(name = "Promotional posts")
@Validated
@RequestMapping("/posts/promotions")
@RestController
public class PromotionalPostController {
  @Autowired
  private PromotionalPostService service;

  @Operation(summary = "List promotional posts by account ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        array = @ArraySchema(schema = @Schema(implementation = AccountModel.class)),
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
  @GetMapping("/{accountId}")
  public Flux<PostResponseModel> list(
    @Valid @PathVariable Integer accountId,
    @Valid @RequestParam(value = "sort", defaultValue = "id") String sort,
    @Valid @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction order,
    @Valid @RequestParam(value = "page", defaultValue = "0") Integer page,
    @Valid @RequestParam(value = "size", defaultValue = "100") Integer size) {
    return this.service.listByAccount(accountId, PageRequest.of(page, size, order, sort));
  }

  @Operation(summary = "Count promotional posts by account ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = CountResponseModel.class),
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
  @GetMapping("/count/{accountId}")
  public Mono<CountResponseModel> count(@Valid @PathVariable Integer accountId) {
    return this.service.countByAccount(accountId);
  }
}
