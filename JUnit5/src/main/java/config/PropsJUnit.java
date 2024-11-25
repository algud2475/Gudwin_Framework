package config;

import org.aeonbits.owner.ConfigFactory;

public interface PropsJUnit extends PropsWeb, PropsDb {
    PropsWeb propsWeb = ConfigFactory.create(PropsWeb.class);
    PropsDb propsDb = ConfigFactory.create(PropsDb.class);

    PropsMq propsMq = ConfigFactory.create(PropsMq.class);
}


