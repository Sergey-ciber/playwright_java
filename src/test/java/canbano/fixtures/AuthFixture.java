package canbano.fixtures;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@UsePlaywright(AuthFixture.TraceOptions.class)
public class AuthFixture {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    String username = dotenv.get("APP_USERNAME");
    String password = dotenv.get("APP_PASSWORD");

    // Штатный способ: JUnit-extension сам стартует трассировку до теста
    // и сохраняет trace.zip после его завершения.
    public static class TraceOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
            return new Options()
                    .setHeadless(isCi) // в CI — headless, локально — headed
                    .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(500))
                    .setTrace(Options.Trace.ON)                          // ON | RETAIN_ON_FAILURE | OFF
                    .setOutputDir(Paths.get("target", "test-results"));
        }
    }

    @BeforeEach
    void authSetup(Page page, BrowserContext context) {
        System.out.println("Запуск авторизации");
        page.navigate("https://app.kanbano.ru/login");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email")).fill(username);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Пароль")).fill(password);
        page.locator("[data-test=\"submit-button\"]").click();
        page.waitForURL("https://app.kanbano.ru/boards");
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("М").setExact(true))).isVisible();

        // 4. Сохраняем состояние в файл
        context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get("playwright/.auth/user.json")));
    }
}