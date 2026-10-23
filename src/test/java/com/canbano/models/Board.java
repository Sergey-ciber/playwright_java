package com.canbano.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Board {
    int id;
    String title;
    String description;
    String color;
    String createdAt;
    int taskCount;
}
