package com.patterns.strategy;

public class StaffPricingStrategy implements PricingStrategy{
    private StaffPricingStrategy() {
    }
    public static double calculatePrice(double basePrice) {
        return basePrice * 0.9; // 10% discount for staff
    }
}
