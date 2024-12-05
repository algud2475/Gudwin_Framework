package config;

import org.apache.commons.lang3.text.WordUtils;

public class KeyConverter {

    public static String access(String key) {
        String[] input = key.split("\\.");
        StringBuilder builder = new StringBuilder();
        builder.append(input[0]);
        for (int i = 1; i < input.length; i++) {
            builder.append(WordUtils.capitalize(input[i]));
        }
        return builder.toString();
    }
}
