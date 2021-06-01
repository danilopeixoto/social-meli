package com.meli.social.api.util;

import com.meli.social.api.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class APIExceptionHandler {
  @ExceptionHandler(ServerWebInputException.class)
  public ResponseEntity<ErrorResponseModel> handleBadRequest(ServerWebInputException exception) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new ErrorResponseModel(
        exception instanceof WebExchangeBindException ?
          Objects.requireNonNull(
            ((WebExchangeBindException) exception)
              .getFieldError())
            .getDefaultMessage() :
          "Bad request."));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponseModel> handleNotFound(NoSuchElementException exception) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(new ErrorResponseModel("Resource not found."));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponseModel> handleInternalServerError(RuntimeException exception) {
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(new ErrorResponseModel("Internal server error."));
  }
}
