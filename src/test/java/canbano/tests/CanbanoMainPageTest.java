package canbano.tests;

import canbano.fixtures.CanbanoMainPageFixture;
import canbano.models.CanbanoMainPage;
import canbano.models.CanbanoMainPageElement;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoMainPageTest extends CanbanoMainPageFixture {

// Проверка видимости элементов главной страницы
    @Test
    void checkElementsMainPageVisible(Page page) {
        CanbanoMainPage mainPage = new CanbanoMainPage(page);
        for (CanbanoMainPageElement el : mainPage.getElements()) {
            assertThat(el.getElementLocator()).isVisible();
            System.out.println("Проверка видимости элемента - " + el.getName());
        }
    }

    // Проверка текста элементов главной страницы
    @Test
    void checkElementsMainPageText(Page page) {
        CanbanoMainPage mainPage = new CanbanoMainPage(page);
        for (CanbanoMainPageElement el : mainPage.getElements()) {
            assertThat(el.getElementLocator()).containsText(el.getText());
            System.out.println("Проверка текста элемента - " + el.getName());
        }
    }
}