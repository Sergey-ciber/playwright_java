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
            JsonNode cookies = root.get("cookies");
            String token = "";
            for (JsonNode cookie : cookies) {
                if (cookie.get("name").asText().equals("access_token")) {
                    token = cookie.get("value").asText();
                    break;
                }
            }
            System.out.println("Токен: " + token);
            return token;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
