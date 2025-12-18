package com.payment;

import java.time.Instant;

public class Payment {
    private static int paymentCount = 0;
    private final int paymentId = paymentCount++;
    private final double amount;
    private final Instant timestamp = Instant.now();

    public Payment(double amount) {
        this.amount = amount;
    }
}
