package com.canbano.models;

import com.canbano.pages.BoardsPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import java.util.concurrent.ThreadLocalRandom;

public class NewBoardForm {

    private final Page page;
    private final PageElement title;
    private final PageElement nameInputFild;
    private final PageElement descriptionInputFild;
    private final PageElement colorsList;
    private final PageElement cancelButtton;
    private final PageElement saveButton;


    public NewBoardForm(Page page) {
        this.page = page;

        // Заголовок "Новая доска"
        this.title = PageElement.builder()
                .elementName("Новая доска")
                .elementLocator(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Новая доска")).describe("Новая доска"))
                .elementText("Новая доска")
                .build();

        // Поле ввода "Название"
        this.nameInputFild = PageElement.builder()
                .elementName("Поле ввода \"Название\"")
                .elementLocator(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Название")))
                .elementAttribute("placeholder")
                .elementAttributeValue("Например, Мой проект")
                .build();

        // Поле ввода "Описание"
        this.descriptionInputFild = PageElement.builder()
                .elementName("Поле ввода \"Описание\"")
                .elementLocator(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Описание")))
                .elementAttribute("placeholder")
                .elementAttributeValue("Описание доски (необязательно)")
                .build();

        // Список цветов
        this.colorsList = PageElement.builder()
                .elementName("Список цветов")
                .elementLocator(page.locator(".color-picker"))
                .build();

        // Кнопка "Отмена"
        this.cancelButtton = PageElement.builder()
                .elementName("Кнопка Отмена")
                .elementLocator(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Отмена")))
                .elementText("Отмена")
                .build();

        // Кнопка "Сохранить"
        this.saveButton = PageElement.builder()
                .elementName("Кнопка Сохранить")
                .elementLocator(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Сохранить")))
                .elementText("Сохранить")
                .build();
    }

    @Step("Ввод названия доски")
    public NewBoardForm fillNewBoardName(String name) {
        nameInputFild.getElementLocator().click();
        nameInputFild.getElementLocator().fill(name);
        return this;
    }


    @Step("Ввод описания доски")
    public NewBoardForm fillNewDescription(String description) {
        descriptionInputFild.getElementLocator().click();
        descriptionInputFild.getElementLocator().fill(description);
        return this;
    }

    @Step("Выбор случайного цвета доски")
    public NewBoardForm selectRandomColor() {
    int colorCount = colorsList.getElementLocator().count();
        colorsList.getElementLocator().nth(ThreadLocalRandom.current().nextInt(0, colorCount)).click();
        return this;
    }

    @Step("Клик по кнопке \"Сохранить\"")
    public NewBoardForm clickSave() {
        saveButton.getElementLocator().click();
        return this;
    }


}
