package com.canbano.config;

import com.canbano.utils.APIToken;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.nio.file.Path;

import static com.canbano.config.ConfigurationManager.config;

public class PlayWrightOptions {

    // Базовый класс с общими настройками (ОБЯЗАТЕЛЬНО static!)
    public static abstract class BaseOptions implements OptionsFactory {
        boolean isCi = System.getenv("CI") != null; // true в GitHub Actions

        protected Options commonOptions() {
            String browserName = config().browser();
            Path outDirPath = Path.of(config().outDirPath());
            Path traceDirPath = Path.of(config().outDirPath(), config().tracePath());
            int slowMotion = config().slowMotion();

            return new Options()
                    .setTrace(Options.Trace.ON)
                    .setBrowserName(browserName)
                    .setHeadless(isCi)
                    .setOutputDir(outDirPath)
                    .setLaunchOptions(new BrowserType.LaunchOptions()
                            .setTimeout(config().timeout())
                            .setTracesDir(traceDirPath)
                            .setSlowMo(isCi ? 50 : slowMotion));
        }
    }

    // UI с авторизацией
    public static class UIWithAuth extends BaseOptions {
        @Override
        public Options getOptions() {
            return commonOptions()
                    .setBaseUrl(isCi ? config().webUiBaseURL() : config().uiBaseURL())
                    .setContextOptions(new Browser.NewContextOptions()
                            .setStorageStatePath(Path.of(config().authPath())));
        }
    }

    // UI без авторизации
    public static class UIWithoutAuth extends BaseOptions {
        @Override
        public Options getOptions() {
            return commonOptions()
                    .setBaseUrl(isCi ? config().webUiBaseURL() : config().uiBaseURL());
        }
    }
}