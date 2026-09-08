package com.buscapecas.app.services.partsDetailsService.records;

public record PartVehicleFitment(
    String vehicleName,
    String trim,
    String engineDesc,
    Integer engineCc,
    Short yearStart,
    Short yearEnd,
    String note
) {
}
