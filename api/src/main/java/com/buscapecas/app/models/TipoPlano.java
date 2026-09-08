package com.buscapecas.app.models;

public enum TipoPlano {

    BASICO(500),
    PREMIUM(5000);

    private final int limiteMensal;

    TipoPlano(int limiteDiario) {
        this.limiteMensal = limiteDiario;
    }

    public int getLimiteDiario() {
        return limiteMensal;
    }
}