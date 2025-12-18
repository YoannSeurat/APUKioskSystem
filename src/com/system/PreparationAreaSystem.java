package com.system;

import java.util.Queue;
import java.util.LinkedList;

import com.order.*;

public class PreparationAreaSystem {
    private static Queue<Order> pendingOrders = new LinkedList<>();
    private static PreparationAreaSystem instance;

    private PreparationAreaSystem() {}

    public static PreparationAreaSystem getInstance() {
        if (PreparationAreaSystem.instance == null) {
            PreparationAreaSystem.instance = new PreparationAreaSystem();
        }
        return PreparationAreaSystem.instance;
    }
    public static Queue<Order> getPendingOrders() {
        return PreparationAreaSystem.pendingOrders;
    }
    public void receiveOrder(Order order) {
        PreparationAreaSystem.pendingOrders.add(order);
        System.out.println("Order " + order.getOrderID() + " received in preparation area");
    }

    public void completeOrder(int orderID) {
        Order order = PreparationAreaSystem.pendingOrders.peek();
        if (order != null && order.getOrderID() == orderID) {
            PreparationAreaSystem.pendingOrders.remove();
            order.setStatus(Status.COMPLETED);
            System.out.println("Order " + orderID + " is ready");
        } else {
            System.out.println("Order " + orderID + " not found in pending orders");
        }
    }

    public void updateOrderStatus(int orderID, Status status) {
        for (Order order : PreparationAreaSystem.pendingOrders) {
            if (order.getOrderID() == orderID) {
                order.setStatus(status);
                System.out.println("Order " + orderID + " status updated to: " + status);
                break;
            }
        }
    }
}
