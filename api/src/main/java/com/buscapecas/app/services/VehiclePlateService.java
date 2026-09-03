package com.buscapecas.app.services;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.buscapecas.app.api.VehicleDataByPlateApi;

@Service
public class VehiclePlateService {

    private final VehicleDataByPlateApi vehicleDataByPlateApi;

    public VehiclePlateService(VehicleDataByPlateApi vehicleDataByPlateApi) {
        this.vehicleDataByPlateApi = vehicleDataByPlateApi;
    }

    public Map<String, Object> buscarPorPlaca(String plate) {
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("A placa do veículo é obrigatória.");
        }

        String carPlateFormated = plate.trim().toUpperCase(Locale.ROOT);
        // regex to verify if the plate is valid: ABC1234 || ABC1D34 -> valids
        boolean isPlateValid = Pattern.matches("^[A-Z]{3}\\d\\w\\d{2}$", carPlateFormated);
        if (!isPlateValid) {
            throw new IllegalArgumentException("Formato de placa inválido");
        }

        return vehicleDataByPlateApi.searchByPlate(carPlateFormated);
    }
}
