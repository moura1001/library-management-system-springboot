package com.moura1001.librarymanagementsystem.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionDetailsResponse> handlerNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().stream().forEach((err) -> {
            String fieldName = ((FieldError)err).getField();
            String msgError = err.getDefaultMessage();

            errors.put(fieldName, msgError);
        });

        return ResponseEntity.badRequest().body(
                new ExceptionDetailsResponse(
                        "Bad Request",
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getClass().toString(),
                        errors
                )
        );
    }

    @ExceptionHandler(DataAccessException.class)
    ResponseEntity<ExceptionDetailsResponse> handlerDataAccessException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionDetailsResponse(
                        "Conflict",
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        ex.getClass().toString(),
                        Map.of(ex.getCause().toString(), ex.getMessage())
                )
        );
    }

    @ExceptionHandler(BookException.class)
    ResponseEntity<ExceptionDetailsResponse> handlerDataAccessException(BookException ex) {
        return ResponseEntity.badRequest().body(
                new ExceptionDetailsResponse(
                        "Bad Request",
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getClass().toString(),
                        Map.of("msg", ex.getMessage())
                )
        );
    }
}
