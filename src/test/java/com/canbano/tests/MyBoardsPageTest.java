package com.canbano.tests;

import com.canbano.config.PlayWrightOptions;
import com.canbano.pages.BoardsPage;
import com.canbano.utils.DateFormatter;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@UsePlaywright(PlayWrightOptions.UIWithAuth.class)
public class MyBoardsPageTest {


    @BeforeEach
    public void openBoardPageTest(Page page) {
        BoardsPage boardsPage = new BoardsPage(page);
        boardsPage.openBoardsPage();
    }


    @Test
    @DisplayName("Проверка элементов на видимость")
    public void checkVisibleElementsTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getProfileInfo().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getExitButton().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getNewBoardButton().getElementLocator()).isVisible()
        );
    }

    @Test
    @DisplayName("Проверка текста на соответствие элементам")
    public void checkTextElementsTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).containsText(boardsPage.getTitle().getElementText()),
                () -> assertThat(boardsPage.getExitButton().getElementLocator()).containsText(boardsPage.getExitButton().getElementText()),
                () -> assertThat(boardsPage.getNewBoardButton().getElementLocator()).containsText(boardsPage.getNewBoardButton().getElementText())
        );
    }

    @Test
    @DisplayName("Проверка атрибутов элементов")
    public void checkAttributesElementsTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).hasAttribute(boardsPage.getTitle()
                        .getElementAttribute(), boardsPage.getTitle().getElementAttributeValue()),
                () -> assertThat(boardsPage.getProfileInfo().getElementLocator()).hasAttribute(boardsPage.getProfileInfo()
                        .getElementAttribute(), boardsPage.getProfileInfo().getElementAttributeValue())
        );
    }

    @Test
    @DisplayName("Удаление всех созданных досок")
    public void deleteAllBoardsTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        boardsPage.deleteAllBoard();

    }


    @Test
    @DisplayName("Создание новой доски")
    public void createNewBoardTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        String newBoardName = DateFormatter.getNowDateAndTime();
        String newBoardDescription = "Описание для доски" + newBoardName;

        boardsPage.createNewBoard(newBoardName, newBoardDescription);

        assertThat(boardsPage.getBoardByName(newBoardName)).isVisible();

    }


    @Test
    @DisplayName("Удаление созданной доски по названию")
    public void deleteBoardByNameTest(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        String newBoardName = DateFormatter.getNowDateAndTime();
        String newBoardDescription = "Описание для доски" + newBoardName;

        boardsPage.createNewBoard(newBoardName, newBoardDescription);

        assertThat(boardsPage.getBoardByName(newBoardName)).isVisible();

        boardsPage.deleteBoardByName(newBoardName);

    }



}
