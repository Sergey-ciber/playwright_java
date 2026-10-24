package com.canbano.tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.canbano.utils.APIToken.getTokenFromAuth;

@UsePlaywright
@Disabled
public class TestToken {
    @Test
    void startToken(Page page) {
        getTokenFromAuth();
    }
}
