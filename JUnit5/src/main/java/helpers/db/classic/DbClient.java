package helpers.db.classic;

import constants.DbTypes;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DbClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(DbClient.class);
    private static BasicDataSource dataSource;
    private DbTypes DB_TYPE;
    private String URL;
    private String USER;
    private String PASSWORD;

    public DbClient(String type, String host, String port, String name, String user, String password) {
        DB_TYPE = DbTypes.getType(type);
        URL = String.format(DB_TYPE.url, host, port, name);
        USER = user;
        PASSWORD = password;

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DB_TYPE.driverName);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        try (Connection connection = dataSource.getConnection()) {
            LOGGER.info("Успешно создан клиент БД с заданными параметрами");
        } catch (SQLException e) {
            Assertions.fail("Ошибка при создании клиента БД - проверьте параметры подключения");
        }
    }

    public int executeUpdate(String query) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            LOGGER.info("Отправка запроса: '{}'", query);
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            Assertions.fail("Не удалось выполнить SQL запрос");
        }
        return 0;
    }

    public <T, C> T executeQuery(String query, ResponseFormat responseFormat, Class<C>... classType) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            LOGGER.info("Отправка запроса: '{}'", query);
            switch (responseFormat) {
                case LIST_OR_ROWS:
                    return (T) ResultSetProcessor.getListOfStringRows(statement.executeQuery(query));
                case MAP_OF_COLUMNS:
                    return (T) ResultSetProcessor.getMapOfColumns(statement.executeQuery(query));
                case POJO:
                    return (T) ResultSetProcessor.getObject(statement.executeQuery(query), classType[0]);
                case LIST_OF_POJO:
                    return (T) ResultSetProcessor.getListObjects(statement.executeQuery(query), classType[0]);
            }
        } catch (SQLException e) {
            Assertions.fail("Не удалось выполнить SQL запрос");
        }
        return null;
    }

    public boolean checkDBTableExist(String tableName) {
        boolean ans = false;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            ans = resultSet.next();
        } catch (SQLException e) {
            LOGGER.info("В БД отсутствует таблица с именем '{}'", tableName);
        }
        return ans;
    }
}
