package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:../JUnit5/src/test/resources/test.properties",
        "file:../TestNG/src/test/resources/test.properties"
})
public interface Props extends Reloadable {
    Props props = ConfigFactory.create(Props.class);

    @Key("screenshotSteps")
    @DefaultValue("false")
    Boolean screenshotSteps();

    @Key("driverPath")
    @DefaultValue("src/test/resources/drivers/chromedriver")
    String driverPath();

    @Key("driverVersion")
    String driverVersion();

    @Key("chromeOptionsJUnit")
    String chromeOptionsJUnit();

    @Key("chromeOptionsTestNG")
    String chromeOptionsTestNG();
}


