package com.canbano.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;


import java.awt.*;
import java.nio.file.Paths;

import static com.canbano.config.ConfigurationManager.config;
import static com.canbano.config.ConfigurationManager.getEnv;


public class AuthPage {

    private final Page page;
    private final String username;
    private final String password;
    private final String authPageURL;
    private final Locator authFormLocator;
    private final Locator inputLogin;
    private final Locator inputPassword;
    private final Locator sendFormButton;
    private final Locator profileIcon;


    public AuthPage(Page page) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.page = page;
        this.username = getEnv("APP_USERNAME");
        this.password = getEnv("APP_PASSWORD");
        this.authPageURL = config().authPageURL();
        this.authFormLocator = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Вход"));
        this.inputLogin = this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Логин"));
        this.inputPassword = this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль"));
        this.sendFormButton = this.page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"));
        this.profileIcon = this.page.locator("#root > div > header > div.navbar-user > a");
    }


    @Step("Переход на страницу авторизации")
    public AuthPage openAuthPage() {
        System.out.println("URL для авторизации - ");
        page.navigate(authPageURL);
        return this;
    }

    @Step("Ввод логина")
    public AuthPage fillLogin(String login) {
        inputLogin.click();
        inputLogin.fill(login);
        return this;
    }

    @Step("Ввод пароля")
    public AuthPage fillPassword(String password) {
        inputPassword.click();
        inputPassword.fill(password);
        return this;
    }

    @Step("Нажатие кнопки Войти")
    public AuthPage clickSubmitButton() {
        sendFormButton.click();
        return this;
    }

    @Step("Сохраняем данные авторизации в playwright/.auth/user.json")
    public AuthPage saveAuthState() {
        page.context().storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get(config().authPath())));
        return this;
    }

    @Step("Получаем локатор формы авторизации")
    public Locator getAuthFormLocator() {
        return authFormLocator;
    }

    @Step("Получаем локатор иконки профиля")
    public Locator getProfileIcon() {
        return profileIcon;
    }
}
