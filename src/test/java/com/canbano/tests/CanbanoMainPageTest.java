package com.canbano.tests;

import com.canbano.fixtures.CanbanoMainPageFixture;
import com.canbano.models.CanbanoMainPage;
import com.canbano.models.CanbanoMainPageElement;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;

import static com.canbano.utils.APIToken.getTokenFromAuth;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoMainPageTest extends CanbanoMainPageFixture {



// Проверка видимости элементов главной страницы
    @Test
    void checkElementsMainPageVisible(Page page) {
        System.out.println("Запуск теста проверки видимости элементов главной страницы");
        CanbanoMainPage mainPage = new CanbanoMainPage(page);
        for (CanbanoMainPageElement el : mainPage.getElements()) {
            assertThat(el.getElementLocator()).isVisible();
            System.out.println("Проверка видимости элемента - " + el.getName());
        }
    }

    // Проверка текста элементов главной страницы
    @Test
    void checkElementsMainPageText(Page page) {
        System.out.println("Запуск теста проверки текста элементов главной страницы");
        CanbanoMainPage mainPage = new CanbanoMainPage(page);
        for (CanbanoMainPageElement el : mainPage.getElements()) {
            assertThat(el.getElementLocator()).containsText(el.getText());
            System.out.println("Проверка текста элемента - " + el.getName());
        }
    }
}