package com.canbano.utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import lombok.NoArgsConstructor;

import java.util.Map;

import static com.canbano.config.ConfigurationManager.config;

@NoArgsConstructor
public final class ApiContextFactory {



    // API с авторизацией
    public static APIRequestContext withAuth(Playwright playwright) {
        boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
        System.out.println("Запуск на Гитхаб - " + isCi);
        System.out.println(config().webApiBaseURL());
        System.out.println(config().webUiBaseURL());
        return playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(isCi ? config().webApiBaseURL() : config().apiBaseURL())
                .setExtraHTTPHeaders(Map.of(
                        "Authorization", "Bearer " + APIToken.getTokenFromAuth(),
                        "Accept", "application/json")));
    }

    // API без авторизации
    public static APIRequestContext withoutAuth(Playwright playwright) {
        boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
        return playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(isCi ? config().webApiBaseURL() : config().apiBaseURL())
                .setExtraHTTPHeaders(Map.of("Accept", "application/json")));
    }
}
