package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     *
     * @param classType - класс возвращаемого объекта
     * @param data - JSON-строка формата String
     * @param path - путь к объекту, если он не находится в корне
     * @return объект класса classType
     * @param <T>
     */
    public static <T> T getObject(Class<T> classType, String data, String... path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (path.length == 0 && !path[0].equals("")) {
                return objectMapper.readValue(data, classType);
            } else {
                String dataOut = objectMapper.readValue(data, JsonNode.class).path(path[0]).toString();
                return objectMapper.readValue(dataOut, classType);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getListObjects(String data, Class<T> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, classType);
        try {
            return objectMapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
