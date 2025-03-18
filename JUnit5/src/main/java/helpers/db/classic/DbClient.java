package helpers.db.classic;

import constants.DbTypes;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.stream.Collectors;

import static helpers.AllureSelenideListener.attachDbRequest;

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
            Assertions.fail("Ошибка при создании клиента БД - проверьте параметры подключения", e);
        }
    }

    public int executeUpdate(String query) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            LOGGER.info("Отправка SQL-запроса: '{}'", query);
            attachDbRequest("Отправка SQL-запроса", query);
            int response = statement.executeUpdate(query);
            LOGGER.info("Ответ на SQL-запрос: '{}'", response);
            attachDbRequest("Ответ на SQL-запрос", String.valueOf(response));
            return response;
        } catch (SQLException e) {
            Assertions.fail("Не удалось выполнить SQL запрос", e);
        }
        return 0;
    }

    public <T> T executeQuery(String query, ResponseFormat responseFormat) {
        return executeQuery(query, responseFormat, null);
    }

    public <T, C> T executeQuery(String query, ResponseFormat responseFormat, Class<C> classType) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            LOGGER.info("Отправка SQL-запроса: '{}'", query);
            attachDbRequest("Отправка SQL-запроса", query);
            ResultSet rs = statement.executeQuery(query);
            switch (responseFormat) {
                case LIST_OR_ROWS:
                    var listOfRows = ResultSetProcessor.getListOfStringRows(statement.executeQuery(query));
                    LOGGER.info("Ответ на SQL-запрос: '{}'", String.valueOf(listOfRows));
                    attachDbRequest("Ответ на SQL-запрос", String.valueOf(listOfRows));
                    return (T) listOfRows;
                case MAP_OF_COLUMNS:
                    var mapOfColumns = ResultSetProcessor.getMapOfColumns(statement.executeQuery(query));
                    LOGGER.info("Ответ на SQL-запрос: '{}'", String.valueOf(mapOfColumns));
                    attachDbRequest("Ответ на SQL-запрос", String.valueOf(mapOfColumns));
                    return (T) mapOfColumns;
                case POJO:
                    var pojo = ResultSetProcessor.getObject(statement.executeQuery(query), classType);
                    LOGGER.info("Ответ на SQL-запрос: '{}'", String.valueOf(pojo));
                    attachDbRequest("Ответ на SQL-запрос", String.valueOf(pojo));
                    return (T) pojo;
                case LIST_OF_POJO:
                    var listOfPojo = ResultSetProcessor.getListObjects(statement.executeQuery(query), classType);
                    String listString = listOfPojo.stream().map(Object::toString).collect(Collectors.joining(", "));
                    LOGGER.info("Ответ на SQL-запрос: '{}'", listString);
                    attachDbRequest("Ответ на SQL-запрос", listString);
                    return (T) listOfPojo;
                case JSON_ARRAY:
                    var jsonArray = ResultSetProcessor.getJsonArray(statement.executeQuery(query));
                    LOGGER.info("Ответ на SQL-запрос: '{}'", jsonArray);
                    attachDbRequest("Ответ на SQL-запрос", jsonArray);
                    return (T) jsonArray;
            }
        } catch (SQLException e) {
            Assertions.fail("Не удалось выполнить SQL запрос", e);
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
        LOGGER.info("В БД присутствует таблица с именем '{}'", tableName);
        return ans;
    }
}
