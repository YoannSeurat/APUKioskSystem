package com.order;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

import com.payment.*;
import com.patterns.strategy.*;
import com.product.*;
import com.system.*;
import com.user.*;

public class Order {
    private static int lastOrderID = 0;
    private final int orderID = lastOrderID++;
    private final User user;
    private Status status;
    private double totalPrice;
    private List<Product> products;
    private final Instant timestamp = Instant.now();

    public Order(User user) {
        this.user = user;
        this.status = Status.PENDING;
        this.totalPrice = 0.0;
        this.products = new ArrayList<>();
    }

    public int getOrderID() {
        return this.orderID;
    }
    public User getUser() {
        return this.user;
    }
    public Status getStatus() {
        return this.status;
    }
    public double getTotalPrice() {
        return this.totalPrice;
    }
    public List<Product> getProducts() {
        return this.products;
    }
    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public double calculateTotalPrice() {
        double sum = 0.0;
        for (Product product : this.products) {
            sum += product.calculatePrice();
        }
        if (user instanceof Lecturer) {
            this.totalPrice = StaffPricingStrategy.calculatePrice(sum);
        } else { // Student
            this.totalPrice = StandardPricingStrategy.calculatePrice(sum);
        }
        return this.totalPrice;
    }
    public void addProduct(Product product) {
        this.products.add(product);
        this.calculateTotalPrice();
    }
    public void removeProduct(Product product) {
        this.products.remove(product);
        this.calculateTotalPrice();
    }

    public void confirmOrder() {
        if (!this.user.deductBalance(this.totalPrice)) {
            this.status = Status.CANCELLED;
            System.out.println("Order " + this.orderID + " confirmation failed: Insufficient balance");
            return;
        }
        PreparationAreaSystem.getInstance().receiveOrder(this);
    }
    public void cancelOrder() {
        this.status = Status.CANCELLED;
        this.user.refundBalance(this.totalPrice);
    }

}
