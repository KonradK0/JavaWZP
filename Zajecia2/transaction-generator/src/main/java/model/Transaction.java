package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {
    private long id;
    private String timestamp;
    private long customerId;
    private ArrayList<Item> items;
    private double sum;

    public Transaction(long id, String timestamp, long customerId, ArrayList<Item> items, double sum) {
        this.id = id;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.items = items;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public long getCustomerId() {
        return customerId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getSum() {
        return sum;
    }
}
