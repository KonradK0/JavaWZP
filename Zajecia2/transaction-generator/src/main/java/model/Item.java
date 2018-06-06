package model;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable{
    @CsvBindByName
    private String name;
    private long quantity;
    @CsvBindByName
    private BigDecimal price;

    public Item() {}

    public Item(String name, long quantity, BigDecimal price) {
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

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
