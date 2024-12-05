package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/testData.properties"
})
public interface PropsTestData extends Config {

    @Key("main.page.url")
    String mainPageUrl();

}
