package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Transaction implements Serializable {
    private long id;
    private String timestamp;
    private long customerId;
    private ArrayList<Item> items;
    private BigDecimal sum;

    public Transaction(long id, String timestamp, long customerId, ArrayList<Item> items, BigDecimal sum) {
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

    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", customerId=" + customerId +
                ", items=" + items +
                ", sum=" + sum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (customerId != that.customerId) return false;
        if (items != null ? !items.equals(that.items) : that.items != null) return false;
        return sum.equals(that.sum);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + sum.hashCode();
        return result;
    }
}
