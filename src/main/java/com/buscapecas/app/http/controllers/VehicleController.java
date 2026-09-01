package com.buscapecas.app.http.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buscapecas.app.services.VehiclePlateService;

@RestController
@RequestMapping("/veiculos")
public class VehicleController {

    private final VehiclePlateService vehiclePlateService;

    public VehicleController(VehiclePlateService vehiclePlateService) {
        this.vehiclePlateService = vehiclePlateService;
    }

    @GetMapping("/placa/{plate}")
    public ResponseEntity<Map<String, Object>> buscarPorPlacaPath(@PathVariable String plate) {
        return ResponseEntity.ok(vehiclePlateService.buscarPorPlaca(plate));
    }
}
