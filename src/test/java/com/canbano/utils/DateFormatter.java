package com.canbano.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public final class DateFormatter {

    // Выносим форматтер в константу (создается 1 раз при загрузке класса)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    public static String getNowDateAndTime() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
