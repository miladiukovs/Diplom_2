package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CreateUserTests {
    private User user;
    private User userWithoutSomeData;
    private UserMethods userMethods;
    private String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        user = UserGenerator.getRandom();
        userWithoutSomeData = UserGenerator.getRandomWithoutLogin();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }

    @Test
    @DisplayName("создание уникального пользоывтеля")
    public void userCanBeCreated() {
        ValidatableResponse response = userMethods.create(user);
        int statusCode = response.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("создать пользователя, который уже зарегистрирован")
    public void usersCantBeTheSame() {
        ValidatableResponse response = userMethods.create(user);
        int statusCode = response.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse responseEqual = userMethods.create(user);
        assertEquals("Пользователь уже занят", 403, responseEqual.extract().statusCode());
    }

    @Test
    @DisplayName("создать пользователя и не заполнить одно из обязательных полей")
    public void userCantBeCreatedWithoutSomeData() {
        ValidatableResponse response = userMethods.create(userWithoutSomeData);
        int statusCode = response.extract().statusCode();
        assertEquals("Email, password and name are required fields", 403, statusCode);
    }
}
