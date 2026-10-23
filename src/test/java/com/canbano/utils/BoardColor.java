package com.canbano.utils;

import lombok.Getter;

import java.util.List;

@Getter
public enum BoardColor {
    BLUE("#0079bf"),
    ORANGE("#d29034"),
    GREEN("#519839"),
    RED("#b04632"),
    PURPLE("#89609e"),
    TEAL("#1d7f8c"),
    PINK("#cd5a91"),
    GRAY("#6b778c");

    private final String hex;

    BoardColor(String hex) {
        this.hex = hex;
    }

    public static final List<BoardColor> ALL = List.of(values());
}
