package com.buscapecas.app.catalog.services;

public record FitmentOption(
    String trim,
    String engineDesc,
    Integer engineCc,
    Short yearStart,
    Short yearEnd,
    String note
) {
}
