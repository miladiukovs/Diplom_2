package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserMethods extends RestClient implements Endpoints {

    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    public void delete(String accessToken) {
        given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .when()
                .delete(DELETE_USER);
    }

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