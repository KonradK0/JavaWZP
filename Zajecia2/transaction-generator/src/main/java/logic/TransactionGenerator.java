package logic;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import logic.IOoperations.InputParser;
import model.Transaction;
import model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import logic.utils.Range;


public class TransactionGenerator {
    private InputParser inputParser;

    private static final Logger logger = LogManager.getLogger(TransactionGenerator.class.getName());

    public TransactionGenerator(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    private Long generateId() {
        Range<Long> range = inputParser.getCustomerIdRange();
        logger.info("Drawing a random customerId");
        return ThreadLocalRandom.current().nextLong(range.getLowBound(), range.getUpBound());
    }

    private String generateDate() {
        Range<OffsetDateTime> range = inputParser.getDateRange();
        Duration between = Duration.between(range.getLowBound(), range.getUpBound());
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(ThreadLocalRandom.current()
                .nextLong(range.getLowBound().toEpochSecond(), range.getLowBound().plusSeconds(between.getSeconds()).toEpochSecond())
                ,0
                , range.getLowBound().getOffset());
        OffsetDateTime randomDate = OffsetDateTime.of(localDateTime, range.getLowBound().getOffset());
        logger.info("Drawing a random date");
        return randomDate.toString();
    }



    private long generateNumberOfItems() {
        Range<Long> range = inputParser.getItemsCountRange();
        logger.info("Drawing a random number of items");
        return ThreadLocalRandom.current().nextLong(range.getLowBound(), range.getUpBound());

    }

    private long generateQuantity() {
        Range<Long> range = inputParser.getItemsQuantity();
        logger.info("Drawing random quantity of an item");
        return ThreadLocalRandom.current().nextLong(range.getLowBound(), range.getUpBound());

    }

    public Transaction generateTransaction(List<Item> itemList, int id){
        String timestamp = generateDate();
        long customerId = generateId();
        long itemsCount = generateNumberOfItems();
        ArrayList<Item> items = new ArrayList<>();
        for (int j = 0; j < itemsCount; j++) {
            Item item = itemList.get(new Random().nextInt(itemList.size()));
            item.setQuantity(generateQuantity());
            logger.info("Creating an item on list");
            items.add(item);
        }
        return new Transaction(id, timestamp, customerId, items, computeSum(items));
    }

    private BigDecimal computeSum(List<Item> list){
        BigDecimal sum = new BigDecimal(0);
        for(Item item : list){
            sum = sum.add(item.getPrice());
        }
        return sum;
    }
}
