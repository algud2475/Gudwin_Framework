package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDate {
    /**
     * Возвращает дату текущего дня в указанном формате
     *
     * @param format формат
     * @return дату дня от текущего минус указанные дни
     */
    public static String getDayCurrent(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Возвращает дату дня от текущего минус указанные дни
     *
     * @param minusDays количество дней
     * @param format формат
     * @return дату дня от текущего минус указанные дни
     */
    public static String getDayBeforeCurrent(long minusDays, String format) {
        return LocalDate.now().plusDays(minusDays).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Возвращает дату дня от текущего плюс указанные дни
     *
     * @param plusDays количество дней
     * @param format формат
     * @return дату дня от текущего плюс указанные дни
     */
    public static String getDayFromCurrent(long plusDays, String format) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern(format));
    }
}
