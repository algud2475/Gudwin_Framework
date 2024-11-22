package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/db.properties"
})
public interface PropsDb extends Config {

    @Key("server")
    String server();

    @Key("port")
    String port();

    @Key("database.name")
    String databaseName();

    @Key("user")
    String user();

    @Key("password")
    String password();
}

