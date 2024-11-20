package org.example;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class UserGenerator {

    public static String getRandomData() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static User getRandom() {
        String email = generateRandomEmail();
        String password = getRandomData();
        String name = getRandomData();
        return new User(email, password, name);
    }

    public static User getRandomWithoutLogin() {
        String password = getRandomData();
        String name = getRandomData();
        return new User(null, password, name);
    }

    public static String generateRandomEmail() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        int length = 6; // длина имени пользователя

        Random random = new Random();
        StringBuilder username = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            username.append(characters.charAt(random.nextInt(characters.length())));
        }

        return username + "@example.com";
    }
}