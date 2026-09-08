package com.buscapecas.app.exceptions;

public class ApiKeyInvalidaException extends RuntimeException {

    public ApiKeyInvalidaException() {
        super("API Key inválida.");
    }
}