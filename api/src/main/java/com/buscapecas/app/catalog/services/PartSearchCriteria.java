package com.buscapecas.app.catalog.services;

public record PartSearchCriteria(
    String vehicleName,
    Integer buildYear,
    Integer modelYear,
    Integer displacement,
    String trim,
    String productGroup
) {
}
