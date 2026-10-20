package com.canbano.tests;

import com.canbano.config.PlayWrightOptions;
import com.canbano.pages.BoardsPage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@UsePlaywright(PlayWrightOptions.UIWithAuth.class)
public class MyBoardsPageTest {


    @Test
    @DisplayName("Проверка элементов на видимость")
    public void checkVisibleElements(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        boardsPage.openBoardsPage();

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getProfileInfo().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getExitButton().getElementLocator()).isVisible(),
                () -> assertThat(boardsPage.getNewBoardButton().getElementLocator()).isVisible()
        );
    }

    @Test
    @DisplayName("Проверка текста на соответствие элементам")
    public void checkTextElements(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        boardsPage.openBoardsPage();

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).containsText(boardsPage.getTitle().getElementText()),
                () -> assertThat(boardsPage.getExitButton().getElementLocator()).containsText(boardsPage.getExitButton().getElementText()),
                () -> assertThat(boardsPage.getNewBoardButton().getElementLocator()).containsText(boardsPage.getNewBoardButton().getElementText())
        );
    }

    @Test
    @DisplayName("Проверка атрибутов элементов")
    public void checkAttributesElements(Page page) {

        BoardsPage boardsPage = new BoardsPage(page);

        boardsPage.openBoardsPage();

        assertAll("Проверка видимости элементов",
                () -> assertThat(boardsPage.getTitle().getElementLocator()).hasAttribute(boardsPage.getTitle()
                        .getElementAttribute(), boardsPage.getTitle().getElementAttributeValue()),
                () -> assertThat(boardsPage.getProfileInfo().getElementLocator()).hasAttribute(boardsPage.getProfileInfo()
                        .getElementAttribute(), boardsPage.getProfileInfo().getElementAttributeValue())
        );
    }
}
