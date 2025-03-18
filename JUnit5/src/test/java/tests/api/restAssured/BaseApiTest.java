package tests.api.restAssured;

import helpers.AllureSelenideListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    protected static AllureSelenideListener allureSelenideListener;

    @BeforeAll
    public static void beforeAllTests() {
//        RestAssured.filters(new AllureRestAssured()
//                .setRequestAttachmentName("Отправка API-запроса:")
//                .setResponseAttachmentName("Ответ на API-запрос:"));
        allureSelenideListener = AllureSelenideListener.getInstance();
    }
}
