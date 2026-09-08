package com.buscapecas.app.http.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buscapecas.app.services.partsDetailsService.PartDetailsService;
import com.buscapecas.app.services.partsDetailsService.records.PartDetails;
import com.buscapecas.app.services.partsService.CompatiblePartsService;
import com.buscapecas.app.services.partsService.PartSearchCriteria;
import com.buscapecas.app.services.partsService.PartSearchResult;

@RestController
@RequestMapping("/parts")
public class CompatiblePartsController {

    private final CompatiblePartsService compatiblePartsService;
    private final PartDetailsService partDetailsService;

    public CompatiblePartsController(
        CompatiblePartsService compatiblePartsService,
        PartDetailsService partDetailsService
    ) {
        this.compatiblePartsService = compatiblePartsService;
        this.partDetailsService = partDetailsService;
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

    @GetMapping("/{partId}/details")
    public ResponseEntity<PartDetails> buscarDetalhes(@PathVariable Long partId) {
        return partDetailsService.findDetails(partId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
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
