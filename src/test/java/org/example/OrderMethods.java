package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods extends RestClient implements Endpoints {

    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(order)
                .post(POST_CREATE_ORDER)
                .then();
    }

    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get(GET_INGREDIENTS_DATA)
                .then();
    }

    public ValidatableResponse getUsersOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .get(GET_USERS_ORDERS)
                .then();
    }

}