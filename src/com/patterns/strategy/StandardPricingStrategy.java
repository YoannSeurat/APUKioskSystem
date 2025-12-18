package com.patterns.strategy;

public class StandardPricingStrategy implements PricingStrategy {
    private StandardPricingStrategy() {
    }
    public static double calculatePrice(double basePrice) {
        return basePrice;
    }
}
