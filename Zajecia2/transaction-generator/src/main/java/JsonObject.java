import java.io.Serializable;
import java.util.ArrayList;

public class JsonObject implements Serializable {
    public long id;
    public String timestamp;
    public long customerId;
    public ArrayList<ItemOnList> items;
    public double sum;

    public JsonObject(long id, String timestamp, long customerId, ArrayList<ItemOnList> items, double sum) {
        this.id = id;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.items = items;
        this.sum = sum;
    }
}
