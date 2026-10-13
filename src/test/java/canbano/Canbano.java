package canbano;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Canbano {

    static Playwright playwright;
    static Browser browser;

    // Новый экземпляр для каждого тестового метода.
    BrowserContext context;
    Page page;

    // Общие для всех тестов в этом классе.
    @BeforeAll
    static void launchBrowser () {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));
    }

    @AfterAll
    static void closeBrowser () {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage () {
        context = browser.newContext();
        // 1. Начинаем запись трассировки
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)   // Записывать скриншоты
                .setSnapshots(true)     // Записывать DOM-снимки
                .setSources(true));     // Записывать исходный код
        page = context.newPage();
        page.navigate("https://kanbano.ru");
        System.out.println(page.title());
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
    }

    @AfterEach
    void closeContext() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @Test
    void checkElementsMainPage() {
        // Ожидаем, что заголовок "содержит" подстроку.
        assertThat(page).hasTitle(Pattern.compile("Бесплатная онлайн Kanban-доска*"));

        // создаем локатор
        Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("О продукте"));

        // Ожидаем, что атрибут "строго равен" значению.
        assertThat(getStarted).hasAttribute("href", "/#advantages");

        // Кликаем по ссылке "get started".
        getStarted.click();

        // Ожидаем, что на странице будет заголовок с именем Installation.
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Знакомая канбан-доска без сложных настроек"))).isVisible();
    }
}

