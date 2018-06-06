package logic;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import logic.IOoperations.InputParser;
import logic.utils.RandomGenerator;
import model.Transaction;
import model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import logic.utils.Range;
import org.springframework.stereotype.Service;

@Service
public class TransactionGenerator {
    private InputParser inputParser;

    private RandomGenerator randomGenerator;

    private static final Logger logger = LogManager.getLogger(TransactionGenerator.class.getName());

    public TransactionGenerator(InputParser inputParser, RandomGenerator randomGenerator) {
        this.inputParser = inputParser;
        this.randomGenerator = randomGenerator;
    }

    Long generateId() {
        Range<Long> range = inputParser.getCustomerIdRange();
        logger.info("Drawing a random customerId");
        return randomGenerator.getRandomLong(range.getLowBound(), range.getUpBound());
    }

    String generateDate() {
        Range<OffsetDateTime> range = inputParser.getDateRange();
        Duration between = Duration.between(range.getLowBound(), range.getUpBound());
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(randomGenerator.getRandomLong
                        (range.getLowBound().toEpochSecond(), range.getLowBound().plusSeconds(between.getSeconds()).toEpochSecond())
                ,0
                , range.getLowBound().getOffset());
        OffsetDateTime randomDate = OffsetDateTime.of(localDateTime, range.getLowBound().getOffset());
        logger.info("Drawing a random date");
        return randomDate.toString();
    }

    long generateNumberOfItems() {
        Range<Long> range = inputParser.getItemsCountRange();
        logger.info("Drawing a random number of items");
        return randomGenerator.getRandomLong(range.getLowBound(), range.getUpBound());

    }

    long generateQuantity() {
        Range<Long> range = inputParser.getItemsQuantity();
        logger.info("Drawing random quantity of an item");
        return randomGenerator.getRandomLong(range.getLowBound(), range.getUpBound());

    }

    public Transaction generateTransaction(List<Item> itemList, int id){
        String timestamp = generateDate();
        long customerId = generateId();
        long itemsCount = generateNumberOfItems();
        ArrayList<Item> items = new ArrayList<>();
        for (int j = 0; j < itemsCount; j++) {
            Item item = itemList.get(randomGenerator.getRandomLong(0, itemList.size()).intValue());
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
