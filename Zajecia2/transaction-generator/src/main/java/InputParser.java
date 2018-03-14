import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InputParser {
    private static InputParser INSTANCE = null;

    Options options = new Options();
    CommandLine line;


    private InputParser(String[] args) {
        buildOptions();
        parseCommandLine(args);
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

    private void parseCommandLine(String[] args) {
        CommandLineParser parser = new BasicParser();
        try {
            this.line = parser.parse(options, args);
        } catch (ParseException e) {
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
            try {
                customerIds[0] = Long.valueOf(customerIdsStr[0]);
                customerIds[1] = Long.valueOf(customerIdsStr[1]);

            } catch (NumberFormatException e) {
                System.out.println("Wrong argument passed, using default");
            }
        }
        return customerIds;
    }

    public String[] getDateRange() {
        String[] dates = new String[2];
        if (line.hasOption("dateRange"))
            dates = line.getOptionValues("dateRange");
        return dates;
    }

    public String getItemsFile() {
        String itemsFile = "items.csv";
        if (line.hasOption("itemsFile"))
            itemsFile = line.getOptionValue("itemsFile");
        return itemsFile;
    }

    public Long[] getItemsCountRange() {
        String[] strItemsCountRange;
        Long[] itemsCountRange = new Long[2];
        itemsCountRange[0] = (long) 1;
        itemsCountRange[1] = (long) 5;
        if (line.hasOption("itemsCount")) {
            strItemsCountRange = line.getOptionValues("itemsCount");
            try {
                itemsCountRange[0] = Long.valueOf(strItemsCountRange[0]);
                itemsCountRange[1] = Long.valueOf(strItemsCountRange[1]);
            } catch (NumberFormatException e) {
                System.out.println("Wrong argument passed, using default");
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
            strItemsQuantity = line.getOptionValues("itemsQuantity");
            try {
                itemsQuantityRange[0] = Long.valueOf(strItemsQuantity[0]);
                itemsQuantityRange[1] = Long.valueOf(strItemsQuantity[1]);
            } catch (NumberFormatException e) {
                System.out.println("Wrong argument passed, using default");
            }
        }
        return itemsQuantityRange;
    }

    public Long getEventsCount() {
        long eventsCount = 1000;
        if (line.hasOption("eventsCount")) {
            try {
                eventsCount = Long.valueOf(line.getOptionValue("eventsCount"));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Wrong argument passed, using default");
            }
        }
        return eventsCount;
    }

    public String getOutDir() {
        String itemsFile = "";
        if (line.hasOption("outDir"))
            itemsFile = line.getOptionValue("outDir");
        return itemsFile;
    }

    public static InputParser getInstance(String[] args) {
        if (INSTANCE == null) {
            INSTANCE = new InputParser(args);
        }
        return INSTANCE;
    }
}
