package com.product;

import java.util.List;
import java.util.ArrayList;

public class Drink extends Product {
    private Size size;
    private Level sugarLevel;
    private Level milkLevel;
    private Level iceLevel;
    private List<String> toppings;

    public Drink(String name, double price, boolean available) {
        super(name, price, available);
        this.size = Size.MEDIUM;
        this.sugarLevel = Level.NORMAL;
        this.milkLevel = Level.NORMAL;
        this.iceLevel = Level.NORMAL;
        this.toppings = new ArrayList<>();
    }

    public Size getSize() {
        return this.size;
    }
    public Level getSugarLevel() {
        return this.sugarLevel;
    }
    public Level getMilkLevel() {
        return this.milkLevel;
    }
    public Level getIceLevel() {
        return this.iceLevel;
    }
    public List<String> getToppings() {
        return this.toppings;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    public void setSugarLevel(Level sugarLevel) {
        this.sugarLevel = sugarLevel;
    }
    public void setMilkLevel(Level milkLevel) {
        this.milkLevel = milkLevel;
    }
    public void setIceLevel(Level iceLevel) {
        this.iceLevel = iceLevel;
    }
    public void addTopping(String topping) {
        this.toppings.add(topping);
    }
    public void removeTopping(String topping) {
        this.toppings.remove(topping);
    }

    @Override
    public double calculatePrice() {
        double finalPrice = this.getPrice();
        switch (this.size) {
            case LARGE -> finalPrice += 2.0;
            case MEDIUM -> finalPrice += 1.0;
            default -> {} // SMALL size, no extra cost
        }
        for (Level level : new Level[]{this.sugarLevel, this.milkLevel, this.iceLevel}) {
            switch (level) {
                case MORE -> finalPrice += 2.0;
                case NORMAL -> finalPrice += 1.0;
                default -> {} // LESS, no extra cost
            }
        }
        finalPrice += this.toppings.size() * 0.5;
        return finalPrice;
    }

    @Override
    public String prettyPrint() {
        return String.format("Drink: %s, price = RM %.2f", this.name, this.calculatePrice());
    }

    @Override
    public String toString() {
        return String.format("Drink{[%d], %s, RM %.2f, %s}", this.productID, this.name, this.price, this.size.toString());
    }

}
