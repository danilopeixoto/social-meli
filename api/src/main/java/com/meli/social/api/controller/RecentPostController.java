package com.meli.social.api.controller;

import com.meli.social.api.model.ErrorResponseModel;
import com.meli.social.api.model.PostResponseModel;
import com.meli.social.api.service.RecentPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Instant;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Tag(name = "Recent posts")
@Validated
@RequestMapping("/posts/recents")
@RestController
public class RecentPostController {
  @Autowired
  private RecentPostService service;

  @Operation(summary = "List recent followed posts by ID", responses = {
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
  @GetMapping("/followed/{accountId}")
  public Flux<PostResponseModel> listRecentFollowed(
    @Valid @PathVariable Integer accountId,
    @Valid @RequestParam(value = "period", defaultValue = "2w") String period,
    @Valid @RequestParam(value = "sort", defaultValue = "id") String sort,
    @Valid @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction order,
    @Valid @RequestParam(value = "page", defaultValue = "0") Integer page,
    @Valid @RequestParam(value = "size", defaultValue = "100") Integer size) {
    Pageable pageable = PageRequest.of(page, size, order, sort);

    return Mono
      .fromCallable(() -> Period.parse('p' + period))
      .onErrorResume(
        DateTimeParseException.class,
        (exception) -> Mono.error(new IllegalArgumentException("Invalid time period.")))
      .map(p -> Instant
        .now()
        .minus(p))
      .flatMapMany(fromDate -> this.service.listRecentFollowed(accountId, fromDate, pageable));
  }
}
