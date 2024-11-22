package helpers.db.classic;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DbObjectMapper<T> {

    private Class clazz;
    private Map<String, Field> fields = new HashMap<>();

    public DbObjectMapper(Class clazz) {
        this.clazz = clazz;

        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        for (Field field : fieldList) {
            Col col = field.getAnnotation(Col.class);
            if (col != null) {
                field.setAccessible(true);
                fields.put(col.name(), field);
            }
        }
    }

    public T map(Map<String, Object> row) {
        try {
            T dto = (T) clazz.getConstructor().newInstance();
            for (Map.Entry<String, Object> entity : row.entrySet()) {
                if (entity.getValue() == null) {
                    continue;
                }
                String column = entity.getKey();
                Field field = fields.get(column);
                if (field != null) {
                    field.set(dto, convertInstanceOfObject(entity.getValue()));
                }
            }
            return dto;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            Assertions.fail("Не удалось преобразовать данные из БД в объект");
        }
        return null;
    }

    public List<T> map(List<Map<String, Object>> rows) {
        List<T> list = new LinkedList<>();

        for (Map<String, Object> row : rows) {
            list.add(map(row));
        }

        return list;
    }

    private T convertInstanceOfObject(Object o) {
        return (T) o;
    }
}
