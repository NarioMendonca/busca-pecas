package com.buscapecas.app.models;

public enum TipoPlano {

    BASICO(500),
    PREMIUM(5000);

    private final int limiteMensal;

    TipoPlano(int limiteMensal) {
        this.limiteMensal = limiteMensal;
    }

    public int getLimiteMensal() {
        return limiteMensal;
    }
}