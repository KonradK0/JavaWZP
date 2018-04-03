package model;

import java.io.Serializable;

public class Item implements Serializable{
    String name;
    long quantity;
    double price;

    public Item(String name, long quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
