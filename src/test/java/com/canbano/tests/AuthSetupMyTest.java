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
public class AuthSetupMyTest {

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
        assertThat(authPage.getProfileIcon()).isVisible();
        authPage.saveAuthState();
    }


}
