package com.patterns.strategy;

public interface PricingStrategy {
    public static double calculatePrice(double basePrice) {
        return basePrice;
    }
}
