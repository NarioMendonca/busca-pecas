package com.buscapecas.app.exceptions;

public class RateLimitExcedidoException extends RuntimeException {

    public RateLimitExcedidoException() {
        super("Limite diário de requisições excedido.");
    }
}