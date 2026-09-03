package com.buscapecas.app.services;

import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.buscapecas.app.api.VehicleDataByPlateApi;

@Service
public class VehiclePlateService {

    private final VehicleDataByPlateApi vehicleDataByPlateApi;

    public VehiclePlateService(VehicleDataByPlateApi vehicleDataByPlateApi) {
        this.vehicleDataByPlateApi = vehicleDataByPlateApi;
    }

    public Map<String, Object> buscarPorPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa do veículo é obrigatória.");
        }

        return vehicleDataByPlateApi.searchByPlate(placa.trim().toUpperCase(Locale.ROOT));
    }
}
