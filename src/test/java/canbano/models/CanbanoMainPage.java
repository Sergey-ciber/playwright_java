package canbano.models;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;

public class CanbanoMainPage {
    private final Page page;
    private final List<Locator> canbanoMainPageLocators;

    public CanbanoMainPage(Page page) {
        this.page = page;
        this.canbanoMainPageLocators = List.of(
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Канбано").setExact(true)),
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("О продукте")),
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Приложение")),
                page.getByRole(AriaRole.BANNER).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Блог")),
                page.getByRole(AriaRole.BANNER).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Тарифы")),
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Попробовать"))
        );
    }

    public List<Locator> getLocators() {
        return canbanoMainPageLocators;
    }
}