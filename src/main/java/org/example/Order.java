package org.example;

import java.util.List;

public class Order {

    private List<String> ingredients = List.of();

    public Order() {
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
