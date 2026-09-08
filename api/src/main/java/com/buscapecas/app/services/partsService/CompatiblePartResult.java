package com.buscapecas.app.services.partsService;

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
