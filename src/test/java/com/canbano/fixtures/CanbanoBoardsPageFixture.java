package com.canbano.fixtures;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Paths;

import static com.canbano.config.ConfigurationManager.config;


@UsePlaywright(CanbanoBoardsPageFixture.TraceOptions.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CanbanoBoardsPageFixture {


    // Штатный способ: JUnit-extension сам стартует трассировку до теста
    // и сохраняет trace.zip после его завершения.
    public static class TraceOptions implements OptionsFactory {

        // Получение URL из конфига
        String uiBaseUrl = config().uiBaseURL();
        String apiBaseUrl = config().apiBaseURL();

        @Override
        public Options getOptions() {
            boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
            return new Options()
                    .setHeadless(isCi) // в CI — headless, локально — headed
                    .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(isCi ? 50 : config().slowMotion()))
                    .setTrace(isCi ? Options.Trace.RETAIN_ON_FAILURE : Options.Trace.ON)                          // ON | RETAIN_ON_FAILURE | OFF
                    .setOutputDir(Paths.get(config().tracePath()))
                    .setContextOptions(new Browser.NewContextOptions()
                            .setStorageStatePath(Paths.get(config().authPath())))
                    .setBaseUrl(uiBaseUrl)
                    .setApiRequestOptions(new APIRequest.NewContextOptions().setBaseURL(apiBaseUrl));
        }
    }

    @BeforeEach
    void createContextAndPage(Page page) {
        page.navigate("/boards");
    }
}
