package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/mq.properties"
})
public interface PropsMq extends Config {

    @Config.Key("host")
    String host();

    @Config.Key("port")
    Integer port();

    @Config.Key("virtual.host")
    String virtualHost();

    @Config.Key("user")
    String user();

    @Config.Key("password")
    String password();
}
