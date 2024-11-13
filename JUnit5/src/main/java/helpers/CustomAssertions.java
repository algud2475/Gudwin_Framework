package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.junit.jupiter.api.Assertions;

import java.util.UUID;

/**
 * Класс пользовательских ассертов.
 * Нужен для встраивания ассертов в шаги, а так же создания собственных ассертов
 */
public class CustomAssertions {
    @Step("Проверить отсутствие ошибки")
    public static void assertTrue(boolean condition) {
        Assertions.assertTrue(condition);
    }

    @Step("Проверить отсутствие ошибки: {message}")
    public static void assertTrue(boolean condition, String message) {
        Assertions.assertTrue(condition, message);
    }

    @Step("Проверить отсутствие ошибки: {message}")
    public static void assertFalse(boolean condition, String message) {
        Assertions.assertFalse(condition, message);
    }

    public static boolean ifTrue(boolean condition, String message) {
        String stepDescription = "Выполнить условную проверку: " + message;
        String uuid = UUID.randomUUID().toString();
        StepResult result = new StepResult().setName(message);
        if (condition) {
            result.setStatus(Status.PASSED);
        } else {
            result.setStatus(Status.SKIPPED);
        }
        Allure.getLifecycle().startStep(uuid, result);
        Allure.getLifecycle().stopStep(uuid);
        return condition;
    }
}
