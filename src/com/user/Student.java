package com.user;

public class Student extends User {
    public Student(String name, double balance) {
        super(name, balance);
    }

    @Override
    public String prettyPrint() {
        return String.format("Student ID: %d, Name: %s, Balance: RM %.2f", this.userID, this.name, this.balance);
    }

    @Override
    public String toString() {
        return String.format("Student{[%d], %s, RM %.2f}", this.userID, this.name, this.balance);
    }
}
