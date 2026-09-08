package com.buscapecas.app.catalog.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buscapecas.app.catalog.services.CompatiblePartsService;
import com.buscapecas.app.catalog.services.PartSearchCriteria;
import com.buscapecas.app.catalog.services.PartSearchResult;

@RestController
@RequestMapping("/parts")
public class CompatiblePartsController {

    private final CompatiblePartsService compatiblePartsService;

    public CompatiblePartsController(CompatiblePartsService compatiblePartsService) {
        this.compatiblePartsService = compatiblePartsService;
    }

    @GetMapping
    public ResponseEntity<PartSearchResult> buscarCompativeis(
        @RequestParam(value = "veiculo", required = false) String vehicleName,
        @RequestParam(value = "anoFabricacao", required = false) Integer buildYear,
        @RequestParam(value = "anoModelo", required = false) Integer modelYear,
        @RequestParam(value = "cilindrada", required = false) Integer displacement,
        @RequestParam(value = "versao", required = false) String trim,
        @RequestParam(value = "grupo", required = false) String productGroup,
        Pageable pageable
    ) {
        var criteria = new PartSearchCriteria(vehicleName, buildYear, modelYear, displacement, trim, productGroup);
        return ResponseEntity.ok(compatiblePartsService.search(criteria, pageable));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<String>> listarGruposDisponiveis(
        @RequestParam(value = "veiculo", required = false) String vehicleName,
        @RequestParam(value = "anoFabricacao", required = false) Integer buildYear,
        @RequestParam(value = "anoModelo", required = false) Integer modelYear,
        @RequestParam(value = "cilindrada", required = false) Integer displacement,
        @RequestParam(value = "versao", required = false) String trim
    ) {
        var criteria = new PartSearchCriteria(vehicleName, buildYear, modelYear, displacement, trim, null);
        return ResponseEntity.ok(compatiblePartsService.listAvailableGroups(criteria));
    }
}
