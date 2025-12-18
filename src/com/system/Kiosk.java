package com.system;

import com.order.*;
import com.product.Menu;
import com.user.*;
import com.patterns.observer.*;

import java.util.List;
import java.util.ArrayList;

public class Kiosk implements MenuObserver {
    private static int kioskCount = 0;
    private final int kioskId = kioskCount++;
    private List<Order> ordersLogs;
    private Order currentOrder;
    private Menu menu;

    public Kiosk(Menu menu) {
        this.ordersLogs = new ArrayList<Order>() {};
        this.currentOrder = null;
        this.menu = menu;
        CentralMenuSystem.getInstance().registerObserver(this);
    }

    public void displayMenu() {
    System.out.printf("""
                ============================
                            MENU
                ----------------------------
                Kiosk ID: %d
                User: %s
                ----------------------------
                %s
                ============================
                %n""",
        this.kioskId, this.currentOrder.getUser().prettyPrint(), this.menu.toString());
    }

    public int getKioskId() {
        return this.kioskId;
    }
    public List<Order> getOrdersLogs() {
        return this.ordersLogs;
    }
    public Order getCurrentOrder() {
        return this.currentOrder;
    }

    public void addLog(Order orderLog) {
        this.ordersLogs.add(orderLog);
    }

    public void createOrder(User user) {
        this.currentOrder = new Order(user);
    }

    public void updateMenu(Menu menu) {
        System.out.println("Updating menu for Kiosk ID: " + this.kioskId);
        this.menu = menu;
    }
    @Override
    public void update(Menu menu) {
        this.updateMenu(menu);
    }
}
