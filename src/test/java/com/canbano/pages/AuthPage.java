package com.canbano.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.github.cdimascio.dotenv.Dotenv;

import java.awt.*;

import static com.canbano.config.ConfigurationManager.config;


public class AuthPage {

    private final Page page;
    private final String username;
    private final String password;
    private final String authPageURL;
    private final Locator authFormLocator;
    private final Locator inputLogin;
    private final Locator inputPassword;
    private final Locator sendFormButton;



    public AuthPage(Page page) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.page = page;
        this.username = System.getenv("APP_USERNAME") != null ? System.getenv("APP_USERNAME") : dotenv.get("APP_USERNAME");;
        this.password = System.getenv("APP_PASSWORD") != null ? System.getenv("APP_PASSWORD") : dotenv.get("APP_PASSWORD");;
        this.authPageURL = config().authPageURL();
        this.authFormLocator = this.page.getByText("ВходВойдите в свой аккаунт для продолженияEmail");
        this.inputLogin = this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email"));
        this.inputPassword = this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль"));
        this.sendFormButton =this.page.locator("[data-test=\"submit-button\"]");
    }






    public static void auth() {

    }
}
