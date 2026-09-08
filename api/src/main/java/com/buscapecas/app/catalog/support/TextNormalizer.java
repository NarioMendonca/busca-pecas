package com.buscapecas.app.catalog.support;

import java.text.Normalizer;
import java.util.Locale;

public final class TextNormalizer {

    private TextNormalizer() {
    }

    public static String code(String value) {
        if (value == null) {
            return "";
        }
        return stripAccents(value).toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "");
    }

    public static String name(String value) {
        if (value == null) {
            return "";
        }
        String cleaned = stripAccents(value).toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9 ./-]", " ");
        return cleaned.replaceAll("\\s+", " ").trim();
    }

    private static String stripAccents(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }
}
