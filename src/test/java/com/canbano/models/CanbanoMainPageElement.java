package com.canbano.models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CanbanoMainPageElement {
    Page page;
    String name;
    Locator elementLocator;
    String text;

    public CanbanoMainPageElement(String name, Locator elementLocator, String text) {
        this.name = name;
        this.elementLocator = elementLocator;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locator getElementLocator() {
        return elementLocator;
    }

    public void setElementLocator(Locator elementLocator) {
        this.elementLocator = elementLocator;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
