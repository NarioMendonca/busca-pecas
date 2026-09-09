package com.buscapecas.app.services;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.buscapecas.app.api.GeminiApi;
import com.buscapecas.app.api.VehicleDataByPlateApi;

@Service
public class VehiclePlateService {

    private static final Logger log = LoggerFactory.getLogger(VehiclePlateService.class);

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

        Map<String, Object> result = new LinkedHashMap<>(vehicleData);
        result.put("friendlyDescription", humanizarOuNulo(vehicleData));
        return result;
    }

    /**
     * A descrição gerada por IA é um extra. Se o Gemini estiver sem chave ou
     * fora do ar, a consulta da placa continua respondendo normalmente — antes,
     * uma falha aqui derrubava a requisição inteira com 500.
     */
    private String humanizarOuNulo(Map<String, Object> vehicleData) {

        if (!geminiApi.isConfigured()) {
            return null;
        }

        try {
            return geminiApi.humanizeVehicleData(vehicleData);
        } catch (RuntimeException e) {
            log.warn("Não foi possível gerar a descrição com o Gemini: {}", e.getMessage());
            return null;
        }
    }
}
