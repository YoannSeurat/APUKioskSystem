package com.patterns.observer;

import java.util.List;
import java.util.ArrayList;

import com.product.*;

public class CentralMenuSystem {
    private static CentralMenuSystem instance;
    private Menu menu;
    private List<MenuObserver> observers;

    private CentralMenuSystem() {
        this.menu = new Menu();
        this.observers = new ArrayList<>();
    }

    public static CentralMenuSystem getInstance() {
        if (instance == null) {
            instance = new CentralMenuSystem();
        }
        return instance;
    }
    public Menu getMenu() {
        return this.menu;
    }
    public List<MenuObserver> getObservers () {
        return this.observers;
    }

    public void registerObserver(MenuObserver observer) {
        this.observers.add(observer);
    }
    public void removeObserver(MenuObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers() {
        for (MenuObserver observer : this.observers) {
            observer.update(this.menu);
        }
    }

    public void updateMenuProduct(int productID, double newPrice, boolean available) {
        Product product = this.menu.getProduct(productID);
        if (product != null) {
            product.setPrice(newPrice);
            product.setAvailability(available);
            notifyObservers();
        }
    }
}
