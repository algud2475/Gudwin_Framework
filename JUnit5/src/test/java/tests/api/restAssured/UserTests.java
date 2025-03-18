package tests.api.restAssured;

import helpers.AllureSelenideListener;
import helpers.CustomAssertions;
import helpers.Specifications;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.User;

import java.util.List;

import static org.hamcrest.Matchers.*;

@DisplayName("Users tests")
public class UserTests extends BaseApiTest {
    @DisplayName("Проверка, что каждый User из списка в составе Avatar содержит id")
    @ParameterizedTest
    @MethodSource("tests.api.restAssured.UserTestsSource#getUsersStream")
    void checkListOfUsers(List<User> users) {
        //users.forEach(user -> Assertions.assertTrue(user.getAvatar().contains(user.getId().toString())));
        CustomAssertions.assertTrue(users.stream().allMatch(u -> u.getAvatar().contains(u.getId().toString())));
    }

    @DisplayName("Проверка, что запрос возвращает корректного User с применением POJO")
    @ParameterizedTest
    @CsvFileSource(resources = "/TestUser.csv", delimiterString = ";", numLinesToSkip = 1)
    void checkUserPojo(@AggregateWith(UserAggregator.class) User userExpected) {
        User userActual = UserTestsSource.getUser();
        Assertions.assertAll(
                () -> Assertions.assertEquals(userExpected.getId(), userActual.getId()),
                () -> Assertions.assertEquals(userExpected.getEmail(), userActual.getEmail()),
                () -> Assertions.assertEquals(userExpected.getFirstName(), userActual.getFirstName()),
                () -> Assertions.assertEquals(userExpected.getLastName(), userActual.getLastName()),
                () -> Assertions.assertEquals(userExpected.getAvatar(), userActual.getAvatar()),
                () -> Assertions.assertEquals(userExpected, userActual)
        );
    }

    @DisplayName("Проверка, что запрос возвращает корректного User средствами RestAssured")
    @Test
    void checkUserRestAssured() {
        Allure.step("Получение юзера");
        User userActual = UserTestsSource.getUser();
        Allure.step("Сравнение юзеров");
        RestAssured
                .given()
                .spec(Specifications.getRequestSpec("https://reqres.in"))
                .when()
                .get("/api/users/2")
                .then()
                .body("data.id", equalTo(userActual.getId()))
                .body("data.email", equalTo(userActual.getEmail()))
                .body("data.first_name", equalTo(userActual.getFirstName()))
                .body("data.last_name", equalTo(userActual.getLastName()))
                .body("data.avatar", equalTo(userActual.getAvatar()));
        Allure.step("Финальный шаг", () -> System.out.println("FINAL"));
        allureSelenideListener.attachApiRequest();
    }
}
