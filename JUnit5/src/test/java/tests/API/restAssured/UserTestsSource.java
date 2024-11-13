package tests.API.restAssured;

import helpers.Specifications;

import io.restassured.RestAssured;
import pojo.User;

import java.util.List;
import java.util.stream.Stream;

public class UserTestsSource {
    private static final String BASE_URL = "https://reqres.in";
    private static final String GET_SINGLE_USER_PATH = "/api/users/2";
    private static final String GET_LIST_USERS_PATH = "/api/users?page=2";

    public static User getUser() {
        return RestAssured
                .given() //заполняем запрос, т.е. auth(), body(), headers(), params(), cookie(), etc
                .spec(Specifications.getRequestSpec(BASE_URL))
                .when() //выполняем запрос, т.е. get(), post(), put(), etc
                .get(GET_SINGLE_USER_PATH)
                .then() //обрабатываем запрос, т.е. assertThat(), extract(), body(), etc
                .spec(Specifications.getResponseSpec())
                .extract()
                .jsonPath()
                .getObject("data", User.class);
        /*
        * Для получения объекта в корне - .extract().as(User.class)
        * Для получения массива объектов в корне - .extract().as(User[].class)
        * Для получения String - .extract().asString()
         */
    }

    public static Stream<List<User>> getUsersStream() {
        Specifications.installSpecification(Specifications.getRequestSpec(BASE_URL), Specifications.getResponseSpec());
        return Stream.of(
                RestAssured
                        .given()
                        .when()
                        .get(GET_LIST_USERS_PATH)
                        .then()
                        .extract()
                        .jsonPath()
                        .getList("data", User.class)
                //Для получения спика объектов в корне - .getList(User.class)
        );
    }
}
