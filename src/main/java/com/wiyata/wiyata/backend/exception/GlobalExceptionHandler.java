package com.wiyata.wiyata.backend.exception;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.payload.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GenericResponse<Object>> handleCustomException(CustomException ex) {
        GenericResponse<Object> errorResponse = ex.toErrorResponse();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorResponse.getStatus()))
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GenericResponse<Object>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = String.format("Metode %s tidak diizinkan untuk endpoint ini.", ex.getMethod());
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), errorMessage, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Object>> handleBadRequestException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage());
        });

        String contentMessage = "Body permintaan atau parameter tidak valid.";
        String errorMessage = "Terjadi kesalahan validasi.";
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GenericResponse<Object>> handleNotFoundException(NoSuchElementException ex) {
        String errorMessage = String.format("Sumber daya dengan ID %s tidak ditemukan.", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage, null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GenericResponse<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        String errorMessage = String.format("Akses tidak sah: %s", ErrorConstant.UNAUTHORIZED.getMessage());
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of(ErrorConstant.UNAUTHORIZED.getMessage()));
        errors.put("details", List.of(ex.getMessage()));
        return buildErrorResponse(ErrorConstant.UNAUTHORIZED.getStatus().value(), errorMessage, errors);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GenericResponse<Object>> handleForbiddenException(ForbiddenException ex) {
        String errorMessage = String.format("Dilarang: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN.value(), errorMessage, null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<GenericResponse<Object>> handleConflictException(ConflictException ex) {
        String errorMessage = String.format("Terjadi konflik: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT.value(), errorMessage, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleInternalServerErrorException(Exception ex) {
        String errorMessage = "Terjadi kesalahan yang tidak terduga.";
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, null);
    }

    private ResponseEntity<GenericResponse<Object>> buildErrorResponse(int statusCode, String message, Object result) {
        GenericResponse<Object> response = new GenericResponse<>(statusCode, message, result);
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
}
