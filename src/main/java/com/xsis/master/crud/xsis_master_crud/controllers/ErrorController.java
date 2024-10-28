package com.xsis.master.crud.xsis_master_crud.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.xsis.master.crud.xsis_master_crud.dtos.responses.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorController {
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
    log.error("An error occured: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    .body(new ErrorResponse<>("error", "Validation failed", e.getConstraintViolations()));
  }
  
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse<String>> handleApiException(ResponseStatusException e) {
    String userFriendlyMessage = switch (e.getStatusCode()) {
        case HttpStatus.NOT_FOUND -> "Not Found";
        default -> "Server Error";
    };
    log.error("An error occured: {}", e.getMessage(), e);
    return ResponseEntity.status(e.getStatusCode())
      .body(new ErrorResponse<>("error", userFriendlyMessage, e.getMessage()));
  }

  // Global Error Handler
  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<ErrorResponse<String>> handleUncaughtExceptions(Exception e) {
  //   log.error("An error occured: {}", e.getMessage(), e);
  //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
  //     .body(new ErrorResponse<>("error", "Server Error", "An internal error occurred"));
  // }
}

