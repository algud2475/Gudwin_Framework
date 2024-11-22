package tests.db;

import helpers.db.classic.DbClient;
import org.junit.jupiter.api.BeforeEach;

import static config.PropsJUnit.propsDb;

public class BaseDbTest {
    protected DbClient dbClient;

    @BeforeEach
    public void beforeEach() {
        this.dbClient = new DbClient(
                propsDb.type(),
                propsDb.host(),
                propsDb.port(),
                propsDb.databaseName(),
                propsDb.user(),
                propsDb.password()
        );
    }

}
