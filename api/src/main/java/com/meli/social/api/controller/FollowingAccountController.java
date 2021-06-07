package com.meli.social.api.controller;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.service.FollowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Tag(name = "Following accounts")
@Validated
@RequestMapping("/accounts/{id}/following")
@RestController
public class FollowingAccountController {
  @Autowired
  private FollowingService service;

  @Operation(summary = "Follow user account", responses = {
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
      responseCode = "403",
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
  @PostMapping("/{followedId}")
  public Mono<AccountModel> follow(
    @Valid @PathVariable Integer id,
    @Valid @PathVariable Integer followedId) {
    return this.service.follow(id, followedId);
  }

  @Operation(summary = "Unfollow user account", responses = {
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
  @DeleteMapping("/{followedId}")
  public Mono<AccountModel> unfollow(
    @Valid @PathVariable Integer id,
    @Valid @PathVariable Integer followedId) {
    return this.service.unfollow(id, followedId);
  }
}
