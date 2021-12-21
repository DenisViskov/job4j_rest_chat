package ru.job4j_rest_chat.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    private ResponseEntity<String> illegalArgumentException() {
        return ResponseEntity.badRequest()
            .body("Id must be greater than zero");
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    private ResponseEntity<String> noSuchElementException() {
        return ResponseEntity.badRequest()
            .body("No such element");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
            e.getBindingResult().getFieldErrors().stream()
                .map(f -> Map.of(
                    f.getField(),
                    String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue())
                ))
                .collect(Collectors.toList())
        );
    }
}
