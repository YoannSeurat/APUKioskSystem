package com.product;

import java.util.List;
import java.util.ArrayList;

public class Sandwich extends Snack {
    private List<String> ingredients;

    public Sandwich(String name, double price, boolean available) {
        super(name, price, available);
        this.ingredients = new ArrayList<>();
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }
    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(String ingredient) {
        this.ingredients.remove(ingredient);
    }

    @Override
    public double calculatePrice() {
        return this.getPrice() + (this.getIngredients().size() * 0.5);
    }

    @Override
    public String prettyPrint() {
        return String.format("Sandwich: %s, price = RM %.2f", this.name, this.calculatePrice());
    }

    @Override
    public String toString() {
        return String.format("Sandwich{[%d], %s, RM %.2f, %d ingredients}", this.productID, this.name, this.price, this.ingredients.size());
    }
}