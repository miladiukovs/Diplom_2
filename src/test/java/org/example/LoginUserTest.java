package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginUserTest {
    private User user;
    private UserMethods userMethods;
    private String accessToken;

    @Before
    public void createUser() {
        user = UserGenerator.getRandom();
        userMethods = new UserMethods();
        ValidatableResponse response = userMethods.create(user);
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }

    @Test
    @DisplayName("логин под существующим пользователем")
    public void userCanLogin() {
        ValidatableResponse loginResponse = userMethods.login(UserCredentials.from(user));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    public void cantLoginWithoutDataLogin() {
        User userWithIncorrectData = UserGenerator.getRandom();
        ValidatableResponse loginResponse = userMethods.login(UserCredentials.from(userWithIncorrectData));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Логин или пароль неверный",401, statusCode);
    }
}