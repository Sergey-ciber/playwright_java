package com.canbano.pages;

import com.canbano.config.PlayWrightOptions;
import com.canbano.models.NewBoardForm;
import com.canbano.models.PageElement;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;


import static com.canbano.config.ConfigurationManager.config;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(PlayWrightOptions.UIWithAuth.class)
@Getter
public class BoardsPage {

    private final Page page;
    private final NewBoardForm newBoardForm;
    private final String boardsPageURL;
    private final PageElement title;
    private final PageElement profileInfo;
    private final PageElement exitButton;
    private final PageElement boardsName;
    private final PageElement newBoardButton;
    private final PageElement emptyWorkArea;
    private final PageElement notEmptyWorkArea;
    private final Locator boardsLocator;
    private final Locator deleteButtonLocator;
    private final Locator submitDeleteButtonLocator;


    public BoardsPage(Page page) {
        this.page = page;
        this.newBoardForm = new NewBoardForm(page);
        this.boardsPageURL = config().boardsPageURL();
        this.title = PageElement.builder().elementName("Мои доски")
                .elementLocator(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Мои доски")).describe("Мои доски"))
                .elementText("Мои доски")
                .elementAttribute("href")
                .elementAttributeValue("/boards")
                .build();


        this.profileInfo = PageElement.builder()
                .elementName("Информация профиля")
                .elementLocator(page.locator("#root > div > header > div.navbar-user > a").describe("Информация профиля"))
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

        this.boardsLocator = page.locator("#root > div > div > div.boards-grid > div");

        // Кнопка удаления доски
        this.deleteButtonLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Удалить"));

        // Кнопка удаления в окне подтверждения
        this.submitDeleteButtonLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Удалить доску"));
    }

    @Step("Переход на страницу /boards")
    public BoardsPage openBoardsPage() {
        page.navigate(boardsPageURL);
        return this;
    }

    @Step("Клик по кнопке Удалить и подтверждение удаления выбранной доски")
    public BoardsPage deleteAndSubmit(Locator board) {

        board.locator(deleteButtonLocator).click();
        submitDeleteButtonLocator.click();
        return this;
    }


    @Step("Удаление доски с названием - {name}")
    public BoardsPage deleteBoardByName(String name) {

        Locator targetBoard = boardsLocator.filter(new Locator.FilterOptions().setHasText(name));
        deleteAndSubmit(targetBoard);
        assertThat(targetBoard).isHidden();
        return this;
    }

    @Step("Поиск доски по названию - {name}")
    public Locator getBoardByName(String name) {

        return boardsLocator.filter(new Locator.FilterOptions().setHasText(name));

    }


    /*
     1. Получаем общее количество досок
     2. Находим первую доску
     3. Нажимаем кнопку "Удалить" у первой доски
     4. Подтверждаем удаление
     5. Проверяем, что общее количество досок уменьшилось
     */
    @Step("Удаление всех досок")
    public BoardsPage deleteAllBoard() {

        while (boardsLocator.count() > 0) {

            int countBefore = boardsLocator.count();
            Locator firstBoard = boardsLocator.first();
            deleteAndSubmit(firstBoard);
            page.waitForCondition(() -> boardsLocator.count() < countBefore);

        }
        return this;
    }


    @Step("Создание новой доски")
    public BoardsPage createNewBoard(String name, String description) {

        newBoardButton.getElementLocator().click();
        newBoardForm.fillNewBoardName(name)
                .fillNewDescription(description)
                .selectRandomColor()
                .clickSave();
        return this;
    }


}
