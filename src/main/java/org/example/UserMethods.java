package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.Endpoints.*;

public class UserMethods extends RestClient {

    @Step("создание нового пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    @Step("авторизация пользователя")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    @Step("удаление пользователя")
    public void delete(String accessToken) {
        given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .when()
                .delete(DELETE_USER);
    }

    @Step("редактирование данных пользователя")
    public ValidatableResponse patch(String accessToken, String body) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(body)
                .when()
                .patch(PATCH_USER)
                .then();
    }
}