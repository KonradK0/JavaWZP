package logic;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import IOoperations.InputParser;
import model.Item;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TransactionGenerator {
    private InputParser inputParser;

    public static final Logger logger = LogManager.getLogger(TransactionGenerator.class.getName());

    TransactionGenerator(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    public Long generateId() {
        Long[] range = inputParser.getCustomerIdRange();
        logger.info("Drawing a random customerId");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);
    }

    public String generateDate() {
        OffsetDateTime[] range = inputParser.getDateRange();
        Duration between = Duration.between(range[0], range[1]);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(ThreadLocalRandom
                .current()
                .nextLong(range[0].toEpochSecond(), range[0].plusSeconds(between.getSeconds()).toEpochSecond()), 0, range[0].getOffset());
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, range[0].getOffset());
        logger.info("Drawing a random date");
        return offsetDateTime.toString();
    }



    public long generateNumberOfItems() {
        Long[] range = inputParser.getItemsCountRange();
        logger.info("Drawing a random number of items");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    public long generateQuantity() {
        Long[] range = inputParser.getItemsQuantity();
        logger.info("Drawing random quantity of an item");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    long generateNumberOfEvents() {
        Long bound = inputParser.getEventsCount();
        logger.info("Drawing a random number of events");
        return ThreadLocalRandom.current().nextLong(bound);

    }

    public Transaction generateTransaction(List<String[]> productNamePriceList, int id){
        String timestamp = generateDate();
        long customerId = generateId();
        long itemsCount = generateNumberOfItems();
        ArrayList<Item> items = new ArrayList<>();
        for (int j = 0; j < itemsCount; j++) {
            String[] itemNamePrice = productNamePriceList.get(new Random().nextInt(productNamePriceList.size()));
            Item item = new Item(itemNamePrice[0], generateQuantity(), Double.valueOf(itemNamePrice[1]));
            logger.info("Creating an item on list");
            items.add(item);
        }
        return new Transaction(id, timestamp, customerId, items, computeSum(items));
    }

    public double computeSum(List<Item> list){
        double sum = 0;
        for(Item item : list){
            sum += item.getPrice() * item.getQuantity();
        }
        return sum;
    }
}
