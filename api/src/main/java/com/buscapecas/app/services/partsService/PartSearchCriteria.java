package com.buscapecas.app.services.partsService;

public record PartSearchCriteria(
    String vehicleName,
    Integer buildYear,
    Integer modelYear,
    Integer displacement,
    String trim,
    String productGroup
) {
}
