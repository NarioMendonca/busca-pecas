package com.buscapecas.app.services.partsService;

public record FitmentOption(
    String trim,
    String engineDesc,
    Integer engineCc,
    Short yearStart,
    Short yearEnd,
    String note
) {
}
