package com.canbano.tests;

import com.canbano.models.Board;
import com.canbano.utils.ApiContextFactory;
import com.canbano.utils.ColorFactory;
import com.canbano.utils.DateFormatter;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static com.canbano.config.ConfigurationManager.config;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@UsePlaywright
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardsPageAPITest {

    @Test
    @DisplayName("Создание новой доски с авторизацией")
    public void createNewBoard(Playwright playwright) {

        // Генерируем название для новой доски
        String boardName = "Доска № " + DateFormatter.getNowDateAndTime();
        // Получаем случайный цвет для доски
        String color = ColorFactory.getRandomColors();

        Board newBoard = Board.builder()
                .title(boardName)
                .color(color)
                .description("Какое то описание для доски " + boardName)
                .build();

        APIRequestContext requestContext = ApiContextFactory.withAuth(playwright);
        APIResponse response = requestContext.post("http://localhost:5173/api/boards",
                RequestOptions.create().setData(newBoard));   // baseURL уже задан
        System.out.println("Status Code: " + response.status());
        System.out.println("Response Body: " + response.text());

        // 1. Проверка статус-кода
        assertEquals(201, response.status(), "Ожидался статус 201 Created");
        assertTrue(response.ok(), "Ответ должен быть успешным");

        // Проверяем данные тела ответа
        JsonObject responseBody = JsonParser.parseString(response.text()).getAsJsonObject();
        assertEquals((boardName), responseBody.get("title").getAsString(),
                "Респонс не вернул доску с названием " + boardName);
    }

    @Test
    @DisplayName("Создание нескольких досок")
    public void createManyBoards(Playwright playwright) {

        int count = 5;
        for(int i = 0; i < count; i++)
            createNewBoard(playwright);
    }


    @Test
    @DisplayName("Создание новой доски без авторизации")
    public void createNewBoardWithoutAuth(Playwright playwright) {

        // Генерируем название для новой доски
        String boardName = "Доска № " + DateFormatter.getNowDateAndTime();
        // Получаем случайный цвет для доски
        String color = ColorFactory.getRandomColors();

        Board newBoard = Board.builder()
                .title(boardName)
                .color(color)
                .description("Описание доски № " + boardName)
                .build();

        APIRequestContext requestContext = ApiContextFactory.withoutAuth(playwright);
        APIResponse response = requestContext.post("http://localhost:5173/api/boards",
                RequestOptions.create().setData(newBoard));   // baseURL уже задан
        System.out.println("Status Code: " + response.status());
        System.out.println("Response Body: " + response.text());

        // 1. Проверка статус-кода
        assertEquals(401, response.status(), "Ожидался статус 403");
    }


    @Test
    @DisplayName("Запрос всех досок")
    public void getBoards(Playwright playwright) {


        APIRequestContext requestContext = ApiContextFactory.withAuth(playwright);
        APIResponse response = requestContext.get(config().boardsPageURL());   // baseURL уже задан
        System.out.println("Status Code: " + response.status());
        System.out.println("Response Body: " + response.text());
    }

}
