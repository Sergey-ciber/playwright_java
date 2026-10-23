package com.canbano.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static com.canbano.config.ConfigurationManager.config;

public class APIToken {

    static String autPath = config().authPath();

    public static String getTokenFromAuth() {
        try {
            JsonNode root = new ObjectMapper().readTree(new File(autPath));

            // Ищем токен в localStorage по всем origin
            for (JsonNode origin : root.get("origins")) {
                for (JsonNode item : origin.get("localStorage")) {
                    if ("token".equals(item.get("name").asText())) {
                        String token = item.get("value").asText();
                        System.out.println("Токен: " + token);
                        return token;
                    }
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
