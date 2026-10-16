package com.canbano.config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
// Настраивается последовательность, откуда брать конфигурационные данные
@Config.Sources({"system:properties", "system:env", "classpath:config.properties", "classpath:allure.properties"})
public interface Configuration extends Config {

    @Key("allure.results.directory")
    String allureResultDir();

    @Key("base.url")
    String baseURL();

    @Key("api.base.url")
    String apiBaseURL();

    @Key("base.test.video.path")
    String baseTestVideoPath();

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

}
