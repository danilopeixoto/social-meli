package com.meli.social.api.controller;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.AccountRequestModel;
import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.service.AccountService;
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

@Tag(name = "Account service")
@Validated
@RequestMapping("/accounts")
@RestController
public class AccountController {
  @Autowired
  private AccountService service;

  @Operation(summary = "Create user account", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = AccountModel.class),
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
  public Mono<AccountModel> create(@Valid @RequestBody Mono<AccountRequestModel> accountRequest) {
    return this.service.create(accountRequest);
  }

  @Operation(summary = "Find account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = AccountModel.class),
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
  public Mono<AccountModel> findById(@Valid @PathVariable Integer id) {
    return this.service
      .findById(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }

  @Operation(summary = "List or find account by username", responses = {
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
      responseCode = "500",
      content = @Content(
        schema = @Schema(implementation = ErrorResponseModel.class),
        mediaType = "application/json"))
  })
  @GetMapping("/")
  public Flux<AccountModel> listOrFindByUsername(
    @Valid @RequestParam(value = "username", required = false) String username) {
    return Mono
      .justOrEmpty(username)
      .flux()
      .switchIfEmpty(Mono.error(new IllegalArgumentException()))
      .flatMap(this.service::findByUsername)
      .onErrorResume(
        IllegalArgumentException.class,
        exception -> this.service.list());
  }

  @Operation(summary = "Update account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = AccountModel.class),
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
  public Mono<AccountModel> update(
    @Valid @PathVariable Integer id,
    @Valid @RequestBody Mono<AccountRequestModel> accountRequest) {
    return this.service
      .update(id, accountRequest)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }

  @Operation(summary = "Delete account by ID", responses = {
    @ApiResponse(
      responseCode = "200",
      content = @Content(
        schema = @Schema(implementation = AccountModel.class),
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
  public Mono<AccountModel> delete(@Valid @PathVariable Integer id) {
    return this.service
      .delete(id)
      .switchIfEmpty(Mono.error(new NoSuchElementException("Resource not found.")));
  }
}
