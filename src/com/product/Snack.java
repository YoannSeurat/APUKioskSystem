package com.product;

public class Snack extends Product {
    public Snack(String name, double price, boolean available) {
        super(name, price, available);
    }
    @Override
    public double calculatePrice() { return this.getPrice(); }

    @Override
    public String prettyPrint() {
        return String.format("Snack: %s, price = RM %.2f", this.name, this.calculatePrice());
    }

    @Override
    public String toString() {
        return String.format("Snack{[%d], %s, RM %.2f, %s}", this.productID, this.name, this.price, this.available ? "Available" : "Unavailable");
    }
}
