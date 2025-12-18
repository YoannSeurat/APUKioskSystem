package com.product;

public abstract class Product {
    private static int lastProductID = 0;
    protected final int productID = lastProductID++;
    protected final String name;
    protected double price; // in MYR
    protected boolean available;

    protected Product(String name, double price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public int getProductID() {
        return this.productID;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public boolean isAvailable() {
        return this.available;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailability(boolean available) {
        this.available = available;
    }

    public double calculatePrice() { return this.getPrice(); }

    public abstract String prettyPrint();

    @Override
    public String toString() {
        return String.format("Product{[%d], %s, RM %.2f}", this.productID, this.name, this.price);
    }
}
