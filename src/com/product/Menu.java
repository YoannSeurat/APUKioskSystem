package com.product;

import java.util.List;
import java.util.ArrayList;

public class Menu {
    private List<Product> catalogue;

    public Menu() {
        this.catalogue = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return this.catalogue;
    }

    public List<Product> getAvailableProducts() {
        return this.catalogue.stream()
                .filter(Product::isAvailable)
                .toList();
    }

    public Product getProduct(int productID) {
        return this.catalogue.stream()
                .filter(p -> p.getProductID() == productID)
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        this.catalogue.add(product);
    }
    public void removeProduct(int productID) {
        this.catalogue.removeIf(p -> p.getProductID() == productID);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : this.catalogue) {
            sb.append(product.prettyPrint()).append("\n");
        }
        return sb.toString();
    }
}
