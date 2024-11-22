package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/db.properties"
})
public interface PropsDb extends Config {

    @Key("type")
    String type();

    @Key("host")
    String host();

    @Key("port")
    String port();

    @Key("database.name")
    String databaseName();

    @Key("user")
    String user();

    @Key("password")
    String password();
}
