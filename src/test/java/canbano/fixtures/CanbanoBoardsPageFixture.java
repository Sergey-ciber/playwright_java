package canbano.fixtures;

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


@UsePlaywright(CanbanoBoardsPageFixture.TraceOptions.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CanbanoBoardsPageFixture {



    // Штатный способ: JUnit-extension сам стартует трассировку до теста
    // и сохраняет trace.zip после его завершения.
    public static class TraceOptions implements OptionsFactory {

        // Полчучение
        private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String uiBaseUrl   = dotenv.get("UI_BASE_URL");
        String apiBaseUrl = dotenv.get("API_BASE_URL");

        @Override
        public Options getOptions() {
            boolean isCi = System.getenv("CI") != null; // true в GitHub Actions
            return new Options()
                    .setHeadless(isCi) // в CI — headless, локально — headed
                    .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(isCi ? 0 : 0))
                    .setTrace(isCi ? Options.Trace.RETAIN_ON_FAILURE : Options.Trace.ON)                          // ON | RETAIN_ON_FAILURE | OFF
                    .setOutputDir(Paths.get("target", "test-results"))
                    .setContextOptions(new Browser.NewContextOptions()
                            .setStorageStatePath(Paths.get("playwright/.auth/user.json")))
                    .setBaseUrl(uiBaseUrl)
                    .setApiRequestOptions(new APIRequest.NewContextOptions().setBaseURL(apiBaseUrl));
        }
    }

    @BeforeEach
    void createContextAndPage (Page page) {
        page.navigate("/boards");
    }
}
