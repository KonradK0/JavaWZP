
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
        String[] range = inputParser.getDateRange();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        OffsetDateTime start;
        OffsetDateTime end;
        try {
            start = OffsetDateTime.parse(range[0], formatter);
            end = OffsetDateTime.parse(range[1], formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            start = OffsetDateTime.parse(LocalDate.now().toString() + "T00:00:00.000-0100", formatter);
            end = OffsetDateTime.parse(LocalDate.now().toString() + "T23:59:59.999-0100", formatter);
            System.out.println("The date format is incorrect. Use \"yyyy-MM-dd'T'HH:mm:ss.SSSXXXX;yyyy-MM-dd'T'HH:mm:ss.SSSXXXX\" for date range. Using default date range");
        }
        Duration between = Duration.between(start, end);
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(ThreadLocalRandom
                .current()
                .nextLong(start.toEpochSecond(), start.plusSeconds(between.getSeconds()).toEpochSecond()), 0, start.getOffset());
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, start.getOffset());
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
