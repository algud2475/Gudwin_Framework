package config;

import org.aeonbits.owner.ConfigFactory;

public interface PropsTestNG {
    PropsWeb propsWeb = ConfigFactory.create(PropsWeb.class);
    PropsDb propsDb = ConfigFactory.create(PropsDb.class);
    PropsTestData propsTestData = ConfigFactory.create(PropsTestData.class);
}


