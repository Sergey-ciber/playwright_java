package canbano.tests;

import canbano.fixtures.CanbanoBoardsPageFixture;
import canbano.models.CanbanoBoardsPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoBoardsPageTest extends CanbanoBoardsPageFixture {


    @Test
    @DisplayName("Создание новой доски")
    void createNewBoard(Page page) {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String boardName = "Доска № " + date + " " + time;
        CanbanoBoardsPage canbanoBoardsPage = new CanbanoBoardsPage(page);
        canbanoBoardsPage.createNewBoard(boardName);
    }

    @Test
    @DisplayName("Создание нескольких досок")
    void createManyNewBoard(Page page) {
        CanbanoBoardsPage canbanoBoardsPage = new CanbanoBoardsPage(page);
        int countBoards = 5;
        List<String> newBoardList = new ArrayList<>();

        for(int i = 0; i < countBoards; i++) {

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            String boardName = "Доска № " + date + " " + time;
            newBoardList.add(boardName);
            canbanoBoardsPage.createNewBoard(boardName);
        }

        if (!canbanoBoardsPage.getAllBoardsLocators().containsAll(newBoardList)) {
            throw new AssertionError(
                    "Не все доски были созданы");
        } else {
            System.out.println("Все доски успешно созданы");
        }

    }

    @Disabled
    @Test
    @DisplayName("Удаление доски по названию")
    void deleteBoard(Page page) {

        CanbanoBoardsPage canbanoBoardsPage = new CanbanoBoardsPage(page);
        canbanoBoardsPage.deleteBoardByName("Доска № 2026-10-15 15:23:51");
    }


    @Test
    @DisplayName("Проверка видимости элементов на странице Доски")
    void checkBoardsPage(Page page) {
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Больше возможностей"))).isVisible();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Доски"))).isVisible();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("М").setExact(true))).isVisible();
        System.out.println("Завершение теста раздела Доски");
    }

    @Test
    @DisplayName("Удаление всех созданных досок")
    void deleteAllBoardsLocators(Page page) {
        CanbanoBoardsPage canbanoBoardsPage = new CanbanoBoardsPage(page);
        canbanoBoardsPage.deleteAllBoards();
    }
}
