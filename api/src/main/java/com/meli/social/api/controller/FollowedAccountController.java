package com.meli.social.api.controller;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.CountResponseModel;
import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.service.FollowedService;
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

@Tag(name = "Followed accounts")
@Validated
@RequestMapping("/accounts/{id}/followed")
@RestController
public class FollowedAccountController {
  @Autowired
  private FollowedService service;

  @Operation(summary = "List followed accounts", responses = {
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
  @GetMapping("/")
  public Flux<AccountModel> list(
    @Valid @PathVariable Integer id,
    @Valid @RequestParam(value = "sort", defaultValue = "id") String sort,
    @Valid @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction order,
    @Valid @RequestParam(value = "page", defaultValue = "0") Integer page,
    @Valid @RequestParam(value = "size", defaultValue = "100") Integer size) {
    return this.service.list(id, PageRequest.of(page, size, order, sort));
  }

  @Operation(summary = "Count followed accounts", responses = {
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
  @GetMapping("/count")
  public Mono<CountResponseModel> count(@Valid @PathVariable Integer id) {
    return this.service.count(id);
  }
}
