package com.canbano.utils;

import java.util.concurrent.ThreadLocalRandom;

public class ColorFactory {

   static BoardColor[] colors = BoardColor.values();
    String color;

    public static String getRandomColors() {
        return colors[ThreadLocalRandom.current().nextInt(0, 7)].getHex();
    }
}
