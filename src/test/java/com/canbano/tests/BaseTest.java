package com.canbano.tests;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import static com.canbano.config.ConfigurationManager.config;

public class BaseTest {
    static {
        PlaywrightAssertions.setDefaultAssertionTimeout(config().timeout());
    }
}