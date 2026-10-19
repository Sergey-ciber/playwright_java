package com.canbano.config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
// Настраивается последовательность, откуда брать конфигурационные данные
@Config.Sources({"system:properties", "system:env", "classpath:config.properties", "classpath:allure.properties"})
public interface Configuration extends Config {

    @Key("allure.results.directory")
    String allureResultDir();

    @Key("ui.base.url")
    String uiBaseURL();

    @Key("api.base.url")
    String apiBaseURL();

    @Key("base.test.video.path")
    String baseTestVideoPath();

    @Key("browser")
    String browser();

    boolean headless();

    @Key("slow.motion")
    int slowMotion();

    int timeout();

    boolean video();

    @Key("auth.path")
    String authPath();

    @Key("trace.path")
    String tracePath();

    @Key("video.path")
    String videoPath();

    @Key("screenshots.path")
    String screenshotsPath();

    @Key("out.dir.path")
    String outDirPath();

    @Key("switch.trace")
    String switchTrace();

    @Key("auth.page.url")
    String authPageURL();
}
