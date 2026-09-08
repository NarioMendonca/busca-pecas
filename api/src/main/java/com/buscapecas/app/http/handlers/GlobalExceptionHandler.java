package com.buscapecas.app.http.handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.buscapecas.app.exceptions.ApiKeyInvalidaException;
import com.buscapecas.app.exceptions.RateLimitExcedidoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiKeyInvalidaException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleApiKeyInvalida(
            ApiKeyInvalidaException exception) {

        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(RateLimitExcedidoException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Map<String, String> handleRateLimitExcedido(
            RateLimitExcedidoException exception) {

        return Map.of("message", exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(
            IllegalArgumentException exception) {

        return Map.of("message", exception.getMessage());
    }
}