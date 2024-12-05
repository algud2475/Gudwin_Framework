package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropsManager {

    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            PROPERTIES = new Properties();
            PROPERTIES.load(new FileInputStream("src/test/resources/db.properties"));
            PROPERTIES.load(new FileInputStream("src/test/resources/testData.properties"));
            PROPERTIES.load(new FileInputStream("src/test/resources/web.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace(); } } }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
