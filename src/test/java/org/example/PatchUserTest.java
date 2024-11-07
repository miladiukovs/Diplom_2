package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatchUserTest {
    private User user;
    private UserMethods userMethods;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.getRandom();
        userMethods = new UserMethods();
        ValidatableResponse response = userMethods.create(user);
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void CleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeUserDataSuccess() {
        String newEmail = "sss@ss.ss";
        String newPassword = "newPassword";
        String newName = "newName";
        String json = getJsonUser(newName, newEmail, newPassword);
        ValidatableResponse response = userMethods.patch(accessToken, json);
        int statusCode = response.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);

        assertEquals(newEmail, response.extract().path("user.email"));
        assertEquals(newName, response.extract().path("user.name"));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserDataError() {
        String newEmail = "sss@ss.ss";
        String newPassword = "newPassword";
        String newName = "newName";
        String json = getJsonUser(newName, newEmail, newPassword);
        ValidatableResponse response = userMethods.patch("", json);
        int statusCode = response.extract().statusCode();
        assertEquals(HttpStatus.SC_UNAUTHORIZED, statusCode);
        assertEquals("You should be authorised", response.extract().path("message"));
    }

    private String getJsonUser(String newName, String newEmail, String newPassword)  {
        ObjectMapper objectMapper = new ObjectMapper();
        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
