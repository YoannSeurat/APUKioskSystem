package com.user;

public class Lecturer extends User{
    public Lecturer(String name, double balance) {
        super(name, balance);
    }

    @Override
    public String prettyPrint() {
        return String.format("Lecturer ID: %d, Name: %s, Balance: RM %.2f", this.userID, this.name, this.balance);
    }

    @Override
    public String toString() {
        return String.format("Lecturer{[%d], %s, RM %.2f}", this.userID, this.name, this.balance);
    }
}
