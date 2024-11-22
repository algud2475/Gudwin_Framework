package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/web.properties"
})
public interface PropsWeb extends Config {

    @Key("screenshot.steps")
    @DefaultValue("false")
    Boolean screenshotSteps();

    @Key("driver.path")
    @DefaultValue("src/test/resources/drivers/chromedriver")
    String driverPath();

    @Key("driver.version")
    String driverVersion();

    @Key("chrome.options")
    String chromeOptions();

    @Key("page.load.timeout")
    Long pageLoadTimeout();

    @Key("pageTimeout")
    String pageTimeout();

    @Key("script.timeout")
    Long scriptTimeout();

    @Key("implicit.wait.timeout")
    Long implicitWaitTimeout();

    @Key("explicit.wait.timeout")
    Long explicitWaitTimeout();
}
