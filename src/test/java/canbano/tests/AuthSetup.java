package canbano.tests;

import canbano.fixtures.AuthFixture;
import org.junit.jupiter.api.Test;

public class AuthSetup extends AuthFixture {
    @Test
    void setup() {
        System.out.println("Авторизация пройдена");
    }
}
