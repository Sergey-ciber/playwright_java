package com.canbano.pages;

import com.microsoft.playwright.Page;

import static com.canbano.config.ConfigurationManager.config;

public abstract class CanbanoMainPage {

    protected Page page;

    public void setAndConfigurationPage(final Page page) {
        this.page = page;
        page.setDefaultTimeout(config().timeout());
    }

    public void initComponents() {};
}
