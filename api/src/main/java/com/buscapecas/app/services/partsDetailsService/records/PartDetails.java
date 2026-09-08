package com.buscapecas.app.services.partsDetailsService.records;

import java.util.List;

public record PartDetails(
    Long partId,
    String code,
    String codeNorm,
    String productGroup,
    String imageFile,
    List<SpecificationItem> specifications,
    List<PartVehicleFitment> vehicles,
    List<CrossReferenceItem> crossReferences
) {
}
