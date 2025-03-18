package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * @param classType - класс возвращаемого объекта
     * @param data      - JSON-строка формата String
     * @param path      - путь к объекту, если он не находится в корне
     * @param <T>
     * @return объект класса classType
     */
    public static <T> T getObject(String data, String path, Class<T> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (path == null) {
                return objectMapper.readValue(data, classType);
            } else {
                String dataOut = objectMapper.readValue(data, JsonNode.class).path(path).toString();
                return objectMapper.readValue(dataOut, classType);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String data, Class<T> classType) {
        return getObject(data, null, classType);
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
