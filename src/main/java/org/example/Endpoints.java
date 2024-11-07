package org.example;

public interface Endpoints {

    String CREATE_USER = "api/auth/register";
    String LOGIN_USER = "api/auth/login";
    String DELETE_USER = "api/auth/user";
    String PATCH_USER = "api/auth/user";

    String GET_INGREDIENTS_DATA = "api/ingredients";
    String POST_CREATE_ORDER = "api/orders";
    String GET_USERS_ORDERS = "api/orders";

}