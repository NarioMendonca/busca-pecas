package com.buscapecas.app.catalog.services;

import java.util.List;

public record CompatiblePartResult(
    Long partId,
    String code,
    String codeNorm,
    String productGroup,
    String imageFile,
    List<FitmentOption> fitments
) {
}
