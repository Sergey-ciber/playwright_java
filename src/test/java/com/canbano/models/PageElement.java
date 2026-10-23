package com.canbano.models;

import com.microsoft.playwright.Locator;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageElement {
    private final String elementName;
    private final Locator elementLocator;
    private final String elementText;
    private final String elementAttribute;
    private final String elementAttributeValue;




}

