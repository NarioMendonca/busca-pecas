package com.buscapecas.app.services.partsService;

import java.util.List;

public record PartSearchResult(
    boolean vehicleFound,
    List<CompatiblePartResult> parts,
    int total
) {
}
