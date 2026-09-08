package com.buscapecas.app.api;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class GeminiApi {

    private static final String API_KEY = "chave da api";

    private static final String MODEL = "gemini-3.6-flash";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GeminiApi() {
        this.restClient = RestClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com/v1beta")
            .build();
        this.objectMapper = new ObjectMapper();
    }

    public String humanizeVehicleData(Map<String, Object> rawData) {
        String prompt = buildPrompt(rawData);

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            )
        );

        Map<String, Object> response = restClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/models/{model}:generateContent")
                .queryParam("key", API_KEY)
                .build(MODEL))
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .retrieve()
            .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        return extractText(response);
    }

    private String buildPrompt(Map<String, Object> rawData) {
        String rawDataJson = toJson(rawData);

        return """
            You are an assistant that translates raw, technical vehicle data, \
            coming from a plate lookup API, into a clear and friendly text for \
            the end user of an auto parts search application.

            Important rules:
            1. Some values in the JSON below may come with several pieces of \
               information concatenated, with no spacing between them (e.g. \
               brand, model, engine and color glued together in a single \
               field). Identify these cases and split that information \
               correctly before presenting it.
            2. Do not make up data that is not present in the JSON.
            3. Ignore null, empty or irrelevant fields for the end user.
            4. Write in Portuguese, in a simple and direct tone, organizing the \
               information into short topics (e.g. Marca, Modelo, Ano, Cor, \
               Motorizacao, Combustivel, Municipio/UF).
            5. Do not include internal technical codes (such as chassis or \
               engine number) unless they are actually relevant to identify \
               the right part.
            6. If the JSON contains a field with a list of items, present them in a \
               clear and organized way, using bullet points or numbered lists.
            7. Keep engine displacement information (if available) in abbreviated format (e.g., 1.0, 1.6, 2.0)

            Raw vehicle data (JSON):
            %s
            """.formatted(rawDataJson);
    }

    private String toJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not convert vehicle data to JSON.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private String extractText(Map<String, Object> response) {
        if (response == null) {
            throw new IllegalStateException("Empty response from the Gemini API.");
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalStateException("No content returned by the Gemini API.");
        }

        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        if (parts == null || parts.isEmpty()) {
            throw new IllegalStateException("Gemini response has no text.");
        }

        return (String) parts.get(0).get("text");
    }
}
