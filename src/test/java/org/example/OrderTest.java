package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderTest {
    private User user;
    private UserMethods userMethods;
    private String accessToken;
    private OrderMethods orderMethods;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userMethods = new UserMethods();
        orderMethods = new OrderMethods();
        ValidatableResponse response = userMethods.create(user);
        accessToken = response.extract().path("accessToken");
        userMethods.login(UserCredentials.from(user));
    }

    @After
    public void CleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);

        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrder() {
        ValidatableResponse ingredientResponse = orderMethods.getIngredients();
        List<Map<String, String>> data = ingredientResponse.extract().path("data");
        List<String> ingredientIds = data.stream()
                .map(ingredient -> ingredient.get("_id"))
                .collect(Collectors.toList());
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(ingredientIds), accessToken);
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, createOrderStatusCode);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        ValidatableResponse ingredientResponse = orderMethods.getIngredients();
        List<Map<String, String>> data = ingredientResponse.extract().path("data");
        List<String> ingredientIds = data.stream()
                .map(ingredient -> ingredient.get("_id"))
                .collect(Collectors.toList());
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(ingredientIds), "");
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, createOrderStatusCode);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(), accessToken);
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, createOrderStatusCode);
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшом ингредиента")
    public void createOrderWitIncorrectIngredients() {
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(List.of("sssss")), accessToken);
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, createOrderStatusCode);
    }

    @Test
    @DisplayName("Получение заказов конкретного авторизованным пользователем")
    public void getUserOrdersWithAuth() {
        ValidatableResponse ingredientResponse = orderMethods.getIngredients();
        List<Map<String, String>> data = ingredientResponse.extract().path("data");
        List<String> ingredientIds = data.stream()
                .map(ingredient -> ingredient.get("_id"))
                .collect(Collectors.toList());
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(ingredientIds), "");
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, createOrderStatusCode);

        ValidatableResponse usersOrders = orderMethods.getUsersOrders(accessToken);
        int usersOrdersStatusCode = usersOrders.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, usersOrdersStatusCode);
    }

    @Test
    @DisplayName("Получение заказов конкретного неавторизованным пользователем")
    public void getUserOrdersWithoutAuth() {
        ValidatableResponse ingredientResponse = orderMethods.getIngredients();
        List<Map<String, String>> data = ingredientResponse.extract().path("data");
        List<String> ingredientIds = data.stream()
                .map(ingredient -> ingredient.get("_id"))
                .collect(Collectors.toList());
        ValidatableResponse createOrderResponse = orderMethods.createOrder(new Order(ingredientIds), "");
        int createOrderStatusCode = createOrderResponse.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, createOrderStatusCode);

        ValidatableResponse usersOrders = orderMethods.getUsersOrders("");
        int usersOrdersStatusCode = usersOrders.extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, usersOrdersStatusCode);
    }
}
