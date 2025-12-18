package com.system;

import java.util.List;

import com.order.*;
import com.product.*;
import com.patterns.observer.*;

public class AdminSystem {
    private static AdminSystem instance;
    private final CentralMenuSystem centralMenuSystem;

    private AdminSystem() {
        this.centralMenuSystem = CentralMenuSystem.getInstance();
    }

    public static AdminSystem getInstance() {
        if (instance == null) {
            instance = new AdminSystem();
        }
        return instance;
    }

    public void updateProductPrice(int productID, double newPrice) {
        Menu menu = this.centralMenuSystem.getMenu();
        Product product = menu.getProduct(productID);
        if (product != null) {
            this.centralMenuSystem.updateMenuProduct(productID, newPrice, product.isAvailable());
            System.out.println("Product " + productID + " price updated to RM " + newPrice);
        }
    }

    public void updateProductAvailability(int productID, boolean available) {
        Menu menu = this.centralMenuSystem.getMenu();
        Product product = menu.getProduct(productID);
        if (product != null) {
            this.centralMenuSystem.updateMenuProduct(productID, product.getPrice(), available);
            System.out.println("Product " + productID + " availability updated to " + available);
        }
    }

    public List<Order> viewTransactionLogs(Kiosk kiosk) {
        return kiosk.getOrdersLogs();
    }
}

