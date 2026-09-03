package com.buscapecas.app.http.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buscapecas.app.services.MockVehiclePlateService;

@RestController
@RequestMapping("/mock/veiculos")
public class MockVehicleController {

    private final MockVehiclePlateService mockVehiclePlateService;

    public MockVehicleController(MockVehiclePlateService mockVehiclePlateService) {
        this.mockVehiclePlateService = mockVehiclePlateService;
    }

    @GetMapping("/placa/{plate}")
    public ResponseEntity<Map<String, Object>> buscarPorPlaca(@PathVariable String plate) {
        return ResponseEntity.ok(mockVehiclePlateService.buscarPorPlaca(plate));
    }
}
