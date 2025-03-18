package helpers.db.classic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;
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

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) { //было ++i, проверить верность
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

    public static ArrayNode getJsonArray(ResultSet rs) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                ObjectNode objectNode = mapper.createObjectNode();

                for (int index = 1; index <= columnCount; index++)
                {
                    String column = rsmd.getColumnName(index);
                    Object value = rs.getObject(column);
                    if (value == null)
                    {
                        objectNode.put(column, "");
                    } else if (value instanceof Integer) {
                        objectNode.put(column, (Integer) value);
                    } else if (value instanceof String) {
                        objectNode.put(column, (String) value);
                    } else if (value instanceof Boolean) {
                        objectNode.put(column, (Boolean) value);
                    } else if (value instanceof Date) {
                        objectNode.put(column, ((Date) value).getTime());
                    } else if (value instanceof Long) {
                        objectNode.put(column, (Long) value);
                    } else if (value instanceof Double) {
                        objectNode.put(column, (Double) value);
                    } else if (value instanceof Float) {
                        objectNode.put(column, (Float) value);
                    } else if (value instanceof BigDecimal) {
                        objectNode.put(column, (BigDecimal) value);
                    } else if (value instanceof Byte) {
                        objectNode.put(column, (Byte) value);
                    } else if (value instanceof byte[]) {
                        objectNode.put(column, (byte[]) value);
                    } else {
                        throw new IllegalArgumentException("Не удалось привести объект к типу данных: " + value.getClass());
                    }
                }

                arrayNode.add(objectNode);
            }
        } catch (SQLException ex) {
            Assertions.fail("Не удалось преобразовать ответ от БД", ex);
        }

        return arrayNode;
    }

    private static ArrayList<Map<String, Object>> getListOfObjectRows(ResultSet resultSet) {
        ArrayList<Map<String, Object>> listOfRows = new ArrayList<>();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) { //было ++i, проверить верность
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
