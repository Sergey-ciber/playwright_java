package canbano.tests;

import canbano.fixtures.CanbanoMainPageFixture;
import canbano.models.CanbanoMainPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoMainPageTest extends CanbanoMainPageFixture {

    @Test
    void checkElementsMainPageVisible(Page page) {
        CanbanoMainPage mainPage = new CanbanoMainPage(page);
        for (Locator loc : mainPage.getLocators()) {
            assertThat(loc).isVisible();
        }
    }
}