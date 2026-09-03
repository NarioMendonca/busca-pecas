package com.buscapecas.app.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class VehicleDataByPlateApi {

    // get data of applications.properties for external api config
    @ConfigurationProperties(prefix = "placas-api")
    public record ApiProperties(String baseUrl, String token) {
    }

    private final RestClient restClient;

    public VehicleDataByPlateApi(@Qualifier("placasApiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public Map<String, Object> searchByPlate(String plate) {
        return restClient.post()
            .uri("/api/v1/placas/numero")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of("placa", plate))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
