import java.io.Serializable;

public class ItemOnList implements Serializable{
    String name;
    long quantity;
    double price;

    public ItemOnList(String name, long quantity, double price) {
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
