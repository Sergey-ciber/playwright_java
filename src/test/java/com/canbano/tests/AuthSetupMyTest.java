package com.canbano.tests;

import com.canbano.config.PlayWrightOptions;
import com.canbano.pages.AuthPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.canbano.config.ConfigurationManager.getEnv;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(PlayWrightOptions.UIWithoutAuth.class)
public class AuthSetupMyTest extends BaseTest{

    private final String username = getEnv("APP_USERNAME");
    private final String password = getEnv("APP_PASSWORD");



    @Test
    @DisplayName("Авторизация")
    public void authorization(Page page) {
        AuthPage authPage = new AuthPage(page);

        authPage.openAuthPage();
        assertThat(authPage.getAuthFormLocator()).isVisible();
        authPage.fillLogin(username)
                .fillPassword(password)
                .clickSubmitButton();
        // Отладка
        page.waitForTimeout(2000);
        System.out.println("Current URL: " + page.url());
        System.out.println("Title: " + page.title());
        System.out.println("Body contains 'Войти': " + page.content().contains("Войти"));
        System.out.println("Profile visible: " + authPage.getProfileIcon().isVisible());
        System.out.println(username);
        System.out.println(password);


        assertThat(authPage.getProfileIcon()).isVisible();
        authPage.saveAuthState();
    }


}
