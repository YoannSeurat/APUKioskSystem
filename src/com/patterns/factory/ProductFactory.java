package com.patterns.factory;

import com.product.*;

public class ProductFactory {
    private ProductFactory() {}

    public static Product createProduct(String type, String name, double price, boolean available) {
        switch(type.toLowerCase()) {
            case "drink" -> {
                return new Drink(name, price, available);
            }
            case "snack" -> {
                return new Snack(name, price, available);
            }
            case "sandwich" -> {
                return new Sandwich(name, price, available);
            }
            default -> throw new IllegalArgumentException("Unknown product type");
        }
    }
}