package com.canbano.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {
    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    public static Configuration config() {
        return ConfigCache.getOrCreate(Configuration.class);
    }

    //Приоритет: переменная окружения > .env
    public static String getEnv(String key) {
        String fromSystem = System.getenv(key);
        return fromSystem != null ? fromSystem : DOTENV.get(key);
    }
}
