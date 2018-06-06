package logic.IOoperations.inputParsers;

import logic.IOoperations.JsonWriter;
import logic.IOoperations.OutputWriter;
import logic.IOoperations.XMLWriter;
import logic.IOoperations.YamlWriter;
import logic.utils.ApplicationWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;
import logic.utils.RandomGenerator;
import logic.utils.Range;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class InputParser {

    private static final Logger logger = LogManager.getLogger(InputParser.class.getName());
    private ApplicationWrapper wrapper;
    private RandomGenerator randomGenerator;


    public InputParser(ApplicationWrapper wrapper, RandomGenerator generator){
        this.wrapper = wrapper;
        this.randomGenerator = generator;
    }

    public Range<Long> getCustomerIdRange() {
        Long DEFAULT_LOWER_BOUND = 1L;
        Long DEFAULT_UPPER_BOUND = 20L;
        Range<Long> customerIdRange = new Range<>(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("customerIds")) {
            String[] customerIdsStr = ((String) arguments.getProperty("customerIds")).split(":");
            logger.info("Trying to parse range of customer ids " + customerIdsStr[0] + ":" + customerIdsStr[1]);
            try {
                customerIdRange.setLowBound(Long.valueOf(customerIdsStr[0]));
                customerIdRange.setUpBound(Long.valueOf(customerIdsStr[1]));
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        logger.info("Returning range of customer ids " + customerIdRange.getLowBound() + ":" + customerIdRange.getUpBound());
        return customerIdRange;
    }

    public Range<OffsetDateTime> getDateRange() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Range<OffsetDateTime> range = new Range<>(OffsetDateTime.parse(LocalDate.now().toString() + "T00:00:00.000-0100", formatter)
                , OffsetDateTime.parse(LocalDate.now().toString() + "T23:59:59.999-0100", formatter));
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("dateRange")) {
            String strRange = (String) arguments.getProperty("dateRange");
            try {
                String firstDate = strRange.substring(0, 28);
                String secondDate = strRange.substring(29);
                range.setLowBound(OffsetDateTime.parse(firstDate, formatter));
                range.setUpBound(OffsetDateTime.parse(secondDate, formatter));
            } catch (DateTimeParseException e) {
                logger.warn("The date format is incorrect. Use \"yyyy-MM-dd'T'HH:mm:ss.SSSXXXX\";\"yyyy-MM-dd'T'HH:mm:ss.SSSXXXX\" for date range. Using default date range");
            }
        }
        return range;
    }

    public String getItemsFile() {
        String itemsFile = "storage/items.csv";
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("itemsFile")) {
            logger.info("Parsing items file name");
            itemsFile = (String) arguments.getProperty("itemsFile");
        }
        return itemsFile;
    }

    public Range<Long> getItemsCountRange() {
        String[] strItemsCountRange;
        Long DEFAULT_LOW_BOUND = 1L;
        Long DEFAULT_UP_BOUND = 5L;
        Range<Long> itemsCountRange = new Range<>(DEFAULT_LOW_BOUND, DEFAULT_UP_BOUND);
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("itemsCount")) {
            strItemsCountRange = ((String) arguments.getProperty("itemsCount")).split(":");
            logger.info("Parsing items count range");
            try {
                itemsCountRange.setLowBound(Long.valueOf(strItemsCountRange[0]));
                itemsCountRange.setUpBound(Long.valueOf(strItemsCountRange[1]));
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return itemsCountRange;
    }

    public Range<Long> getItemsQuantity() {
        String[] strItemsQuantity;
        Long DEFAULT_LOW_BOUND = 1L;
        Long DEFAULT_UP_BOUND = 30L;
        Range<Long> itemsQuantityRange = new Range<Long>(DEFAULT_LOW_BOUND, DEFAULT_UP_BOUND);
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("itemsQuantity")) {
            logger.info("Parsing items quantity range");
            strItemsQuantity = ((String) arguments.getProperty("itemsQuantity")).split(":");
            try {
                itemsQuantityRange.setLowBound(Long.valueOf(strItemsQuantity[0]));
                itemsQuantityRange.setUpBound(Long.valueOf(strItemsQuantity[1]));
            } catch (NumberFormatException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return itemsQuantityRange;
    }

    public Long getEventsCount() {
        long eventsCount = 1000;
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("eventsCount")) {
            logger.info("Parsing events count");
            try {
                eventsCount = Long.valueOf((String) arguments.getProperty("eventsCount"));
            } catch (NumberFormatException | NullPointerException e) {
                logger.warn("Wrong argument passed, using default");
            }
        }
        return randomGenerator.getRandomLong(0, eventsCount);
    }

    public String getOutDir() {
        String outDir = "./";
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if (arguments.containsProperty("outDir")) {
            logger.info("Parsing items file name");
            outDir = (String) arguments.getProperty("outDir");
        }
        return outDir;
    }

    public OutputWriter getOutputWriter(){
        PropertySource arguments =  wrapper.getWrappedContext().getEnvironment().getPropertySources().get("arguments");
        if(arguments.containsProperty("format")){
            logger.info("Parsing output format");
            String format = (String) arguments.getProperty("format");
            if("xml".equals(format)){
                return new XMLWriter();
            }
            else if("yaml".equals(format))
                return new YamlWriter();
        }
        return new JsonWriter();
    }
}
