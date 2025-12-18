package com.user;

public abstract class User {
    private static int lastID = 0;
    protected final int userID = User.lastID++;
    protected final String name;
    protected double balance; // in MYR

    protected User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public int getUserID() {
        return this.userID;
    }
    public String getName() {
        return this.name;
    }
    public double getBalance() {
        return this.balance;
    }

    public boolean deductBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    public void refundBalance(double amount) {
        this.balance += amount;
    }

    public abstract String prettyPrint();
}


