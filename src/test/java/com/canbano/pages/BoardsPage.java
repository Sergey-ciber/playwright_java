package com.canbano.pages;

import com.canbano.config.PlayWrightOptions;
import com.canbano.models.PageElement;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.canbano.config.ConfigurationManager.config;

@UsePlaywright(PlayWrightOptions.UIWithAuth.class)
@Getter
public class BoardsPage {

    private final Page page;
    private final String boardsPageURL;
    private final PageElement title;
    private final PageElement profileInfo;
    private final PageElement exitButton;
    private final PageElement boardsName;
    private final PageElement newBoardButton;
    private final PageElement emptyWorkArea;
    private final PageElement notEmptyWorkArea;


    public BoardsPage(Page page) {
        this.page = page;
        this.boardsPageURL = config().boardsPageURL();
        this.title = PageElement.builder().elementName("Task Manager")
                .elementLocator(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("🗂 Task Manager")).describe("Task Manager"))
                .elementText("Task Manager")
                .elementAttribute("href")
                .elementAttributeValue("/boards")
                .build();


        this.profileInfo = PageElement.builder()
                .elementName("Информация профиля")
                .elementLocator(page.locator("#root > div > header > div > a").describe("Информация профиля"))
                .elementAttribute("href")
                .elementAttributeValue("/profile")
                .build();

        this.exitButton = PageElement.builder()
                .elementName("Выйти")
                .elementLocator(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Выйти")).describe("-> Выйти <-"))
                .elementText("Выйти")
                .build();

        this.boardsName = PageElement.builder()
                .elementName("Мои доски")
                .elementLocator(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Мои доски")).describe("Мои доски"))
                .elementText("Мои доски")
                .build();

        this.newBoardButton = PageElement.builder()
                .elementName("+ Новая доска")
                .elementLocator(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+ Новая доска")).describe("*** + Новая доска ***"))
                .elementText("+ Новая доска")
                .build();

        this.emptyWorkArea = PageElement.builder()
                .elementName("Пустая рабочая область")
                .elementLocator(page.locator("#root > div > div > div.empty-state").describe("Пустая рабочая область"))
                .elementText("У вас пока нет досок.Создайте первую доску, чтобы начать работу с заданиями")
                .build();


            this.notEmptyWorkArea = PageElement.builder()
            .elementName("Не пустая рабочая область")
                .elementLocator(page.locator("#root > div > div > div.boards-grid").describe("Не пустая рабочая область"))
                .build();
}
    @Step("Переход на страницу /boards")
    public BoardsPage openBoardsPage() {
        page.navigate(boardsPageURL);
        return this;
    }


}
