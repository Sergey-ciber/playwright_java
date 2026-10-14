package canbano.tests;

import canbano.fixtures.CanbanoBoardsPageFixture;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CanbanoBoardsPageTest extends CanbanoBoardsPageFixture {

    @Test
    void checkBoardsPage(Page page) {
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Больше возможностей"))).isVisible();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Доски"))).isVisible();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("М").setExact(true))).isVisible();
        System.out.println("Завершение теста раздела Доски");
    }
}
