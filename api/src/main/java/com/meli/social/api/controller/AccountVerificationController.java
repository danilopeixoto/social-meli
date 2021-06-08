package com.meli.social.api.controller;

import com.meli.social.api.model.AccountModel;
import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.service.AccountVerificationService;
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

@Tag(name = "Account verifications")
@Validated
@RequestMapping("/accounts/{id}")
@RestController
public class AccountVerificationController {
  @Autowired
  private AccountVerificationService service;

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
  @PostMapping("/verifications")
  public Mono<AccountModel> verify(
    @Valid @PathVariable Integer id,
    @Valid @RequestParam(defaultValue = "true") Boolean verified) {
    return this.service.verify(id, verified);
  }
}
