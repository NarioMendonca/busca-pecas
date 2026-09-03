package com.buscapecas.app.services;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class MockVehiclePlateService {

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

        Map<String, Object> vehicle = new LinkedHashMap<>();
        vehicle.put("id", 645711654);
        vehicle.put("cor", "PRATA");
        vehicle.put("chassi", "9BGRG48F0CG233401");
        vehicle.put("motor", "NAB275995");
        vehicle.put("municipio", "TAUA");
        vehicle.put("uf", "CE");
        vehicle.put("placa_modelo_antigo", "OCJ8796");
        vehicle.put("placa_modelo_novo", "OCJ8H96");
        vehicle.put("ano_fabricacao", "2011");
        vehicle.put("marca", "GM");
        vehicle.put("ano_modelo", "2012");
        vehicle.put("modelo", "CHEVROLET/CELTA 1.0L LS");
        vehicle.put("segmento", "AUTO");
        vehicle.put("sub_segmento", "AU - ENTRADA");
        vehicle.put("grupo", "CELTA");
        vehicle.put("combustivel", "ALCOOL / GASOLINA");
        vehicle.put("nacionalidade", "NACIONAL");
        vehicle.put("especie", "PASSAGEIRO");
        vehicle.put("tipo_veiculo", "AUTOMOVEL");
        vehicle.put("uf_placa", "CE");
        vehicle.put("potencia", "78");
        vehicle.put("capacidade_carga", null);
        vehicle.put("quantidade_passageiro", "5");
        vehicle.put("cilindradas", "1000");
        vehicle.put("cap_maxima_tracao", "200");
        vehicle.put("peso_bruto_total", "130");
        vehicle.put("created_at", "2011-09-19T03:00:00.000Z");
        vehicle.put("updated_at", "2011-09-19T03:00:00.000Z");
        return vehicle;
    }
}
