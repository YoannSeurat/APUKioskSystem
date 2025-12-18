package com.system;

import java.util.Map;
import java.util.HashMap;

import com.user.*;

public class APCardSystem {
    private static APCardSystem instance;
    private final Map<Integer, User> users;

    private APCardSystem() {
        this.users = new HashMap<>();
    }

    public static APCardSystem getInstance() {
        if (APCardSystem.instance == null) {
            APCardSystem.instance = new APCardSystem();
        }
        return APCardSystem.instance;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public User getUserById(int userID) {
        return users.get(userID);
    }

    public void addUser(User user) {
        users.put(user.getUserID(), user);
    }

    public void removeUser(int userID) {
        users.remove(userID);
    }

    public boolean deductBalance(int userID, double amount) {
        User user = getUserById(userID);
        if (user == null) {
            return false;
        }
        return user.deductBalance(amount);
    }

    public boolean refundBalance(int userID, double amount) {
        User user = getUserById(userID);
        if (user == null) {
            return false;
        }
        user.refundBalance(amount);
        return true;
    }
}