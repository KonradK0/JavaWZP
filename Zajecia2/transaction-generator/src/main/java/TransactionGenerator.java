
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TransactionGenerator {
    private InputParser inputParser;

    public static final Logger logger = LogManager.getLogger(TransactionGenerator.class.getName());

    TransactionGenerator(String[] args) {
        inputParser = InputParser.getInstance(args);
    }

    //Constructor used only in tests
    TransactionGenerator(InputParser inputParser){
        this.inputParser = inputParser;
    }

    Long generateId() {
        Long[] range = inputParser.getCustomerIdRange();
        logger.info("Drawing a random customerId");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);
    }

    String generateDate() {
        OffsetDateTime[] range = inputParser.getDateRange();
        Duration between = Duration.between(range[0], range[1]);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(ThreadLocalRandom
                .current()
                .nextLong(range[0].toEpochSecond(), range[0].plusSeconds(between.getSeconds()).toEpochSecond()), 0, range[0].getOffset());
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, range[0].getOffset());
        logger.info("Drawing a random date");
        return offsetDateTime.toString();
    }

    String getFileName() {
        return inputParser.getItemsFile();
    }


    long generateNumberOfItems() {
        Long[] range = inputParser.getItemsCountRange();
        logger.info("Drawing a random number of items");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    long generateQuantity() {
        Long[] range = inputParser.getItemsQuantity();
        logger.info("Drawing random quantity of an item");
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    long generateNumberOfEvents() {
        Long bound = inputParser.getEventsCount();
        logger.info("Drawing a random number of events");
        return ThreadLocalRandom.current().nextLong(bound);

    }

    String getOutDir() {
        return inputParser.getOutDir();
    }
}
