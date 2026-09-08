package com.buscapecas.app.services;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.buscapecas.app.api.GeminiApi;
import com.buscapecas.app.api.VehicleDataByPlateApi;

@Service
public class VehiclePlateService {

    private final VehicleDataByPlateApi vehicleDataByPlateApi;
    private final GeminiApi geminiApi;

    public VehiclePlateService(VehicleDataByPlateApi vehicleDataByPlateApi, GeminiApi geminiApi) {
        this.vehicleDataByPlateApi = vehicleDataByPlateApi;
        this.geminiApi = geminiApi;
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

        Map<String, Object> vehicleData = vehicleDataByPlateApi.searchByPlate(carPlateFormated);
        String friendlyDescription = geminiApi.humanizeVehicleData(vehicleData);

        Map<String, Object> result = new LinkedHashMap<>(vehicleData);
        result.put("friendlyDescription", friendlyDescription);
        return result;
    }
}
