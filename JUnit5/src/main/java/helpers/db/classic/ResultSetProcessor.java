package helpers.db.classic;

import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultSetProcessor {

    public static ArrayList<Map<String, String>> getListOfStringRows(ResultSet resultSet) {
        ArrayList<Map<String, String>> listOfRows = new ArrayList<>();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                    row.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }

                listOfRows.add(row);
            }

        } catch (SQLException ex) {
            Assertions.fail("Не удалось преобразовать ответ от БД", ex);
        }

        return listOfRows;
    }

    public static Map<String, ArrayList<String>> getMapOfColumns(ResultSet resultSet) {
        HashMap<String, ArrayList<String>> mapOfColumns = new HashMap<>();

        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                mapOfColumns.put(resultSetMetaData.getColumnName(i), new ArrayList<>());
            }

            while (resultSet.next()) {
                Iterator iterator = mapOfColumns.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, ArrayList<String>> entry = (Map.Entry) iterator.next();
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(resultSet.getString((String) entry.getKey()));
                    entry.setValue((ArrayList) Stream.concat(arrayList.stream(), ((ArrayList) entry.getValue()).stream()).collect(Collectors.toList()));
                }
            }
        } catch (SQLException ex) {
            Assertions.fail("Не удалось преобразовать ответ от БД", ex);
        }

        return mapOfColumns;
    }

    public static <T> T getObject(ResultSet resultSet, Class<T> classType) {
        return new DbObjectMapper<T>(classType).map(getListOfObjectRows(resultSet).get(0));
    }

    public static <T> List<T> getListObjects(ResultSet resultSet, Class<T> classType) {
        return new DbObjectMapper<T>(classType).map(getListOfObjectRows(resultSet));
    }

    private static ArrayList<Map<String, Object>> getListOfObjectRows(ResultSet resultSet) {
        ArrayList<Map<String, Object>> listOfRows = new ArrayList<>();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                    row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }

                listOfRows.add(row);
            }

        } catch (SQLException ex) {
            Assertions.fail("Не удалось преобразовать ответ от БД", ex);
        }

        return listOfRows;
    }
}
