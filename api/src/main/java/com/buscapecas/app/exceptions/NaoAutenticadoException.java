package com.buscapecas.app.exceptions;

public class NaoAutenticadoException extends RuntimeException {

    public NaoAutenticadoException() {
        super("Você precisa estar autenticado (fazer login) para acessar esse recurso.");
    }
}
