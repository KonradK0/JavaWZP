
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class TransactionGenerator {
    private InputParser inputParser;

    TransactionGenerator(String[] args) {
        inputParser = InputParser.getInstance(args);
    }

    //Constructor used only in tests
    TransactionGenerator(InputParser inputParser){
        this.inputParser = inputParser;
    }

    Long generateId() {
        Long[] range = inputParser.getCustomerIdRange();
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);
    }

    String generateDate() {
        OffsetDateTime[] range = inputParser.getDateRange();
        Duration between = Duration.between(range[0], range[1]);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(ThreadLocalRandom
                .current()
                .nextLong(range[0].toEpochSecond(), range[0].plusSeconds(between.getSeconds()).toEpochSecond()), 0, range[0].getOffset());
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, range[0].getOffset());
        return offsetDateTime.toString();
    }

    String getFileName() {
        return inputParser.getItemsFile();
    }


    long generateNumberOfItems() {
        Long[] range = inputParser.getItemsCountRange();
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    long generateQuantity() {
        Long[] range = inputParser.getItemsQuantity();
        return ThreadLocalRandom.current().nextLong(range[0], range[1]);

    }

    long generateNumberOfEvents() {
        Long bound = inputParser.getEventsCount();
        return ThreadLocalRandom.current().nextLong(bound);

    }

    String getOutDir() {
        return inputParser.getOutDir();
    }
}
