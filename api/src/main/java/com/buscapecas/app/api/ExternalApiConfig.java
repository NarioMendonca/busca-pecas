package com.buscapecas.app.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.buscapecas.app.api.VehicleDataByPlateApi.ApiProperties;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class ExternalApiConfig {

    @Bean("placasApiRestClient")
    public RestClient placasApiRestClient(ApiProperties properties) {
        return RestClient.builder()
            .baseUrl(properties.baseUrl())
            .defaultHeader("Authorization", "Bearer " + properties.token())
            .build();
    }
}
