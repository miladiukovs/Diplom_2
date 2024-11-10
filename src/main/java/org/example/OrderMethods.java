package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods extends RestClient implements Endpoints {

    @Step("создание заказа")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(order)
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step("получение данных ингредиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get(GET_INGREDIENTS_DATA)
                .then();
    }

    @Step("получение данных заказа пользователя")
    public ValidatableResponse getUsersOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .get(GET_USERS_ORDERS)
                .then();
    }

}