import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ThreadLocalRandom;


public class InputParser {
    private static InputParser INSTANCE = null;

    Options options = new Options();
    CommandLine line;

    public static final Logger logger = LogManager.getLogger(InputParser.class.getName());
    //Constructor only used in tests
    public InputParser(CommandLine line) {
        this.line = line;
    }

    private InputParser(String[] args) {
        buildOptions();
        CommandLineParser parser = new BasicParser();
        parseCommandLine(args, parser);
    }

    private void buildOptions() {
        options.addOption(OptionBuilder.hasArgs(2).withValueSeparator(':').withDescription("range of customer ids").create("customerIds"));
        options.addOption(OptionBuilder.hasArgs(2).withValueSeparator(';').withDescription("range of dates used to create timestamp").create("dateRange"));
        options.addOption(OptionBuilder.hasArg().withDescription("name of file containing items").create("itemsFile"));
        options.addOption(OptionBuilder.hasArgs(2).withValueSeparator(':').withDescription("range of amount of items in \"items\" table").create("itemsCount"));
        options.addOption(OptionBuilder.hasArgs(2).withValueSeparator(':').withDescription("range of quantities of items").create("itemsQuantity"));
        options.addOption(OptionBuilder.hasArg().withDescription("number of transactions to generate").create("eventsCount"));
        options.addOption(OptionBuilder.hasArg().withDescription("name of folder to save output").create("outDir"));
    }

    private void parseCommandLine(String[] args, CommandLineParser parser) {
        try {
            this.line = parser.parse(options, args);
            logger.info("Parsing command line input");
        } catch (ParseException e) {
            logger.warn("Error parsing command line");
            e.printStackTrace();
        }
    }

    public Long[] getCustomerIdRange() {
        String[] customerIdsStr;
        Long[] customerIds = new Long[2];
        customerIds[0] = (long) 1;
        customerIds[1] = (long) 20;
        if (line.hasOption("customerIds")) {
            customerIdsStr = line.getOptionValues("customerIds");
            logger.info("Trying to parse range of customer ids " + customerIdsStr[0] + ":" + customerIdsStr[1]);
            try {
                customerIds[0] = Long.valueOf(customerIdsStr[0]);
                customerIds[1] = Long.valueOf(customerIdsStr[1]);
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        logger.info("Returning range of customer ids " + customerIds[0] + ":" + customerIds[1]);
        return customerIds;
    }

    public OffsetDateTime[] getDateRange() {
        String[] range;
        OffsetDateTime[] dateRange = new OffsetDateTime[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        dateRange[0] = OffsetDateTime.parse(LocalDate.now().toString() + "T00:00:00.000-0100", formatter);
        dateRange[1] = OffsetDateTime.parse(LocalDate.now().toString() + "T23:59:59.999-0100", formatter);
        if (line.hasOption("dateRange")) {
            range = line.getOptionValues("dateRange");
            try {
                dateRange[0] = OffsetDateTime.parse(range[0], formatter);
                dateRange[1] = OffsetDateTime.parse(range[1], formatter);
            } catch (DateTimeParseException e) {
                dateRange[0] = OffsetDateTime.parse(LocalDate.now().toString() + "T00:00:00.000-0100", formatter);
                dateRange[1] = OffsetDateTime.parse(LocalDate.now().toString() + "T23:59:59.999-0100", formatter);
                logger.warn("The date format is incorrect. Use \"yyyy-MM-dd'T'HH:mm:ss.SSSXXXX\";\"yyyy-MM-dd'T'HH:mm:ss.SSSXXXX\" for date range. Using default date range");
            }
        }
        return dateRange;
    }

    public String getItemsFile() {
        String itemsFile = "items.csv";
        if (line.hasOption("itemsFile")) {
            logger.info("Parsing items file name");
            itemsFile = line.getOptionValue("itemsFile");
        }
        return itemsFile;
    }

    public Long[] getItemsCountRange() {
        String[] strItemsCountRange;
        Long[] itemsCountRange = new Long[2];
        itemsCountRange[0] = (long) 1;
        itemsCountRange[1] = (long) 5;
        if (line.hasOption("itemsCount")) {
            strItemsCountRange = line.getOptionValues("itemsCount");
            logger.info("Parsing items count range");
            try {
                itemsCountRange[0] = Long.valueOf(strItemsCountRange[0]);
                itemsCountRange[1] = Long.valueOf(strItemsCountRange[1]);
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return itemsCountRange;
    }

    public Long[] getItemsQuantity() {
        String[] strItemsQuantity;
        Long[] itemsQuantityRange = new Long[2];
        itemsQuantityRange[0] = (long) 1;
        itemsQuantityRange[1] = (long) 30;
        if (line.hasOption("itemsQuantity")) {
            logger.info("Parsing items quantity range");
            strItemsQuantity = line.getOptionValues("itemsQuantity");
            try {
                itemsQuantityRange[0] = Long.valueOf(strItemsQuantity[0]);
                itemsQuantityRange[1] = Long.valueOf(strItemsQuantity[1]);
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return itemsQuantityRange;
    }

    public Long getEventsCount() {
        long eventsCount = 1000;
        if (line.hasOption("eventsCount")) {
            logger.info("Parsing events count");
            try {
                eventsCount = Long.valueOf(line.getOptionValue("eventsCount"));
            } catch (NumberFormatException | NullPointerException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return eventsCount;
    }

    public String getOutDir() {
        String outDir = "";
        if (line.hasOption("outDir")) {
            logger.info("Parsing items file name");
            outDir = line.getOptionValue("outDir");
        }
        return outDir;
    }

    public static InputParser getInstance(String... args) {
        if (INSTANCE == null) {
            logger.debug("Creating new InputParser instance");
            INSTANCE = new InputParser(args);
        }
        return INSTANCE;
    }
}
