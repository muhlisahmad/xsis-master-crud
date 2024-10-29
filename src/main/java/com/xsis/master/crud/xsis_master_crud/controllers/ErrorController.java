package com.xsis.master.crud.xsis_master_crud.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.xsis.master.crud.xsis_master_crud.dtos.responses.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorController {
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
    List<Map<String, String>> errors = new ArrayList<>();
    
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      errors.add(Map.of(
        "field", violation.getPropertyPath().toString(),
        "message", violation.getMessage()
      ));
    }

    log.error("An error occured: {}", e.getMessage(), e);
    
    ErrorResponse<List<Map<String, String>>> response = new ErrorResponse<>(
      "fail", 
      "Validation failed", 
      errors
    );
    
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<Map<String, String>> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
      errors.add(Map.of(
        "field", error.getField(),
        "message", error.getDefaultMessage()
      ));
    });

    log.error("An error occured: {}", e.getMessage(), e);

    ErrorResponse<List<Map<String, String>>> response = new ErrorResponse<>(
      "fail",
      "Validation failed for arguments",
      errors
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.error("An error occured: {}", e.getMessage(), e);

    ErrorResponse<String> response = new ErrorResponse<>(
      "fail",
      "Malformed request body",
      e.getMessage()
    );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
    log.error("An error occured: {}", e.getMessage(), e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail",
      "Missing request parameter",
      e.getParameterName() + " parameter is missing"
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse<String>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    log.error("An error occured: {}", e.getMessage(), e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail",
      "Unsupported media type",
      e.getMessage()
    );

    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<ErrorResponse<String>> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    String errorMessage = "The requested media type is not acceptable. Supported media types are: " + e.getSupportedMediaTypes();

    log.error("An error occured: {}", errorMessage, e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail",
      "Media type not acceptable", 
      errorMessage
    );

    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse<?>> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    List<Map<String, String>> errors = new ArrayList<>();
    e.getAllValidationResults().forEach(validationResult -> {
      System.out.println(validationResult.getResolvableErrors());
      errors.add(Map.of(
        "field", validationResult.getMethodParameter().getParameterName(),
        "message", validationResult.getResolvableErrors().getFirst().getDefaultMessage()
      ));
    });

    ErrorResponse<List<Map<String, String>>> response = new ErrorResponse<>(
      "fail", 
      "Validation failed", 
      errors
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse<String>> handleApiException(ResponseStatusException e) {
    log.error("An error occured: {}", e.getMessage(), e);
    System.out.println("triggered");

    ErrorResponse<String> response = new ErrorResponse<String>(
      e.getStatusCode().value() < 500 ? "fail" : "error",
      e.getReason() != null ? e.getReason() : "Unexpected error", 
      e.getMessage());

    return new ResponseEntity<>(response, e.getStatusCode());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    String errorMessage = 
    "Parameter " 
    + e.getName() 
    + " should be of type ";
    Class<?> requiredTypeArgument = e.getRequiredType();
    errorMessage += (requiredTypeArgument != null)
      ? requiredTypeArgument.getSimpleName()
      : "unknown";

    log.error("An error occured: {}", errorMessage, e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail", 
      "Invalid parameter type", 
      errorMessage
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    String errorMessage = "Request method " + e.getMethod() + " is not supported for this route";

    log.error("An error occured: {}", errorMessage, e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail", 
      "Method not supported", 
      errorMessage
    );

    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse<String>> handleNoHandlerFoundException(NoHandlerFoundException e) {
    String errorMessage = 
      "No resource found for "
      + e.getHttpMethod()
      + " "
      + e.getRequestURL();

      log.error("An error occured: {}", errorMessage, e);

      ErrorResponse<String> response = new ErrorResponse<String>(
        "fail",
        "Not found", 
        errorMessage
      );

      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    String errorMessage = "Data conflict: " + e.getMostSpecificCause().getMessage();

    log.error("An error occured: {}", errorMessage, e);
    ErrorResponse<String> response = new ErrorResponse<String>(
      "fail", 
      "Data conflict", 
      errorMessage
    );

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }
  
  // Global Error Handler
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse<String>> handleUncaughtExceptions(Exception e) {
    log.error("An error occured: {}", e.getMessage(), e);

    ErrorResponse<String> response = new ErrorResponse<String>(
      "error",
      "Server error", 
      "An internal error occurred"
    );

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

