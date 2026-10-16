package com.canbano.models;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;

public class CanbanoMainPage {
    private final Page page;
    private final List<CanbanoMainPageElement> canbanoMainPageElements;

    public CanbanoMainPage(Page page) {
        this.page = page;
        this.canbanoMainPageElements = List.of(
                new CanbanoMainPageElement("Канбано", page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Канбано")
                        .setExact(true)),
                        "Канбано"),

                new CanbanoMainPageElement("О продукте", page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("О продукте")
                        .setExact(true)),
                        "О продукте"),
                new CanbanoMainPageElement("Приложение", page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Приложение")
                        .setExact(true)),
                        "Приложение"),
                new CanbanoMainPageElement("Блог", page.getByRole(AriaRole.BANNER).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions()
                        .setName("Блог")),
                        "Блог"),
                new CanbanoMainPageElement("Тарифы", page.getByRole(AriaRole.BANNER).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions()
                        .setName("Тарифы")),
                        "Тарифы"),
                new CanbanoMainPageElement("Попробовать", page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Попробовать")),
                        "Попробовать")
        );
    }

    public List<CanbanoMainPageElement> getElements() {
        return canbanoMainPageElements;
    }

}