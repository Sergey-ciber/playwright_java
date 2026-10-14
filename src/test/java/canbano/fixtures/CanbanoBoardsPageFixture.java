package canbano.fixtures;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;


@UsePlaywright(CanbanoBoardsPageFixture.TraceOptions.class)
public class CanbanoBoardsPageFixture {

    // Штатный способ: JUnit-extension сам стартует трассировку до теста
    // и сохраняет trace.zip после его завершения.
    public static class TraceOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
            return new Options()
                    .setHeadless(isCi) // в CI — headless, локально — headed
                    .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(1000))
                    .setTrace(Options.Trace.ON)                          // ON | RETAIN_ON_FAILURE | OFF
                    .setOutputDir(Paths.get("target", "test-results"))
                    .setContextOptions(new Browser.NewContextOptions()
                            .setStorageStatePath(Paths.get("playwright/.auth/user.json")));
        }
    }

    @BeforeEach
    void createContextAndPage (Page page) {
        page.navigate("https://app.kanbano.ru/boards");
        System.out.println(page.title());
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
    }
}
