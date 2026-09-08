package com.buscapecas.app.catalog.services;

import java.util.List;

public record PartSearchResult(
    boolean vehicleFound,
    List<CompatiblePartResult> parts,
    int total
) {
}
