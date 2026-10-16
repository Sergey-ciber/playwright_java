package com.canbano.tests;

import com.canbano.fixtures.AuthFixture;
import org.junit.jupiter.api.Test;

public class AuthSetup extends AuthFixture {
    @Test
    void setup() {
        System.out.println("Авторизация пройдена");
    }
}
