package canbano.models;

import canbano.fixtures.CanbanoBoardsPageFixture;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoBoardsPage extends CanbanoBoardsPageFixture {

    String boardName;
    Page page;


    public CanbanoBoardsPage(Page page) {
        this.page = page;
    }

    // Создание новой доски
    public void createNewBoard(String boardName) {

        assertThat(this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Запиши свои идеи в новой доске"))).isVisible();
        this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Запиши свои идеи в новой доске")).click();
        this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Запиши свои идеи в новой доске")).fill(boardName);
        assertThat(this.page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Запиши свои идеи в новой доске"))).hasValue(boardName);
        this.page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Создать доску")).click();
        assertThat(this.page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(boardName))).isVisible();
        this.page.navigate("/boards");
        assertThat(page.locator("#app")).containsText(boardName);
        assertThat(this.page.locator("#app > div > div.grow.overflow-y-hidden.sm\\:rounded-\\[24px\\] > div > div:nth-child(1) > div > div.h-full.w-full.overflow-y-auto.grow.px-\\[8px\\].sm\\:px-\\[16px\\].pb-\\[8px\\].sm\\:pb-\\[16px\\]"))
                .isVisible();
    }

    // Получение списка локаторов всех карточек досок
    public List<String> getAllBoardsLocators() {
        List<Locator> boardCardsLocators;
        List<String> boardCardNames = new ArrayList<>();
        this.page.navigate("/boards");
        assertThat(this.page.locator("#app > div > div.grow.overflow-y-hidden.sm\\:rounded-\\[24px\\] > div > div:nth-child(1) > div > div.h-full.w-full.overflow-y-auto.grow.px-\\[8px\\].sm\\:px-\\[16px\\].pb-\\[8px\\].sm\\:pb-\\[16px\\]"))
                .isVisible();
        boardCardsLocators = this.page.locator("#app > div > div.grow.overflow-y-hidden.sm\\:rounded-\\[24px\\] > div > div:nth-child(1) > div > div.h-full.w-full.overflow-y-auto.grow.px-\\[8px\\].sm\\:px-\\[16px\\].pb-\\[8px\\].sm\\:pb-\\[16px\\] > div:nth-child(1) > div> *").all();
        if (boardCardsLocators.isEmpty()) {
            throw new AssertionError(
                    "На странице /boards не найдено ни одной карточки доски");
        }
        System.out.println("Найдены доски с названиями:");
        for (Locator card : boardCardsLocators) {
            boardCardNames.add(card.textContent());
        }
        return boardCardNames;
    }


    /**
     * Удаляет доску с указанным именем.
     */
    public void deleteBoardByName(String name) {
        // 0. Переходим в список досок
        this.page.navigate("/boards");

        //Ждем прогрузку всех ресурсов
        this.page.waitForLoadState(LoadState.LOAD);

        // 1. Все карточки досок
        Locator boardCards = this.page.locator("#app > div > div.grow.overflow-y-hidden.sm\\:rounded-\\[24px\\] > div > div:nth-child(1) > div > div.h-full.w-full.overflow-y-auto.grow.px-\\[8px\\].sm\\:px-\\[16px\\].pb-\\[8px\\].sm\\:pb-\\[16px\\] > div:nth-child(1) > div > *");

        // 2. Карточка именно нужной доски (а не первая в списке)
        Locator boardCard = boardCards
                .filter(new Locator.FilterOptions().setHasText(name))
                .first();
        assertThat(boardCard).isVisible();

        // 3. Наводимся на карточку и открываем её меню «⋮» (относительно этой карточки)
        boardCard.hover();
        boardCard.locator("div.absolute.top-\\[12px\\].right-\\[12px\\]").first().click();

        // 4. Пункт «Удалить» в меню (роль может быть MENUITEM или BUTTON)
        Locator deleteItem = this.page.getByRole(AriaRole.MENUITEM,
                new Page.GetByRoleOptions().setName("Удалить"));
        if (deleteItem.count() == 0) {
            deleteItem = this.page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Удалить"));
        }
        deleteItem.click();

        // 5. Подтверждение удаления, если появляется диалог
        Locator confirmButton = this.page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Удалить"));
        if (confirmButton.count() > 0) {
            confirmButton.click();
        }


        assertThat(page.locator("body > div.max-w-full.fixed.top-\\[12px\\].sm\\:top-\\[24px\\].left-1\\/2.-translate-x-1\\/2.flex.flex-col.gap-\\[12px\\].z-notification"))
                .isVisible();

        this.page.navigate("/boards");


        // 6. Проверяем, что доска исчезла из списка
        assertThat(this.page.locator("#app")).not().containsText(name);
    }

    // Удаление всех созданных досок
    public void deleteAllBoards() {
        List<String> allBoardsName = getAllBoardsLocators();
        System.out.println("Получен список досок: " + allBoardsName.toString());

        if (!allBoardsName.isEmpty()) {
            for (String name : allBoardsName) {
                System.out.println("Удаляю доску - " + name);
                deleteBoardByName(name);
            }
            System.out.println("Все доски удалены");
        } else {
            System.out.println("Нет досок, доступных для удаления");
        }
    }

    // Получение всех досок api
    public void getAllBoardsAPI() {

    }
}
