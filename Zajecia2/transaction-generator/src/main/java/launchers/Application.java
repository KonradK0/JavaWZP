package launchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import logic.IOoperations.CSVInputReader;
import logic.IOoperations.inputParsers.InputParser;
import logic.IOoperations.OutputWriter;
import logic.IOoperations.inputParsers.PropertiesInputParser;
import logic.TransactionGenerator;
import logic.utils.ApplicationWrapper;
import logic.utils.RandomGenerator;
import model.Item;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@Configuration
@PropertySource(value = "classpath:generator.properties", name = "arguments")
public class Application {

    private static AnnotationConfigApplicationContext ctx;
    private static final Logger logger = LogManager.getLogger(Application.class.getName());

    public static void main(String... args) {
        if (args.length > 0) {
            ctx = createContext(new SimpleCommandLinePropertySource("arguments", args));
            logger.debug("Creating Spring Context with CommandLinePropertySource");
        } else {
            ctx = createContextWithMapPropertySource();
            logger.debug("Creating Spring Context with MapPropertySource");
        }
        ApplicationWrapper wrapper = (ApplicationWrapper) ctx.getBean("ApplicationWrapper");
        RandomGenerator generator = (RandomGenerator) ctx.getBean("RandomGenerator");
        InputParser inputParser = (InputParser) ctx.getBean("inputParser", wrapper, generator);
        inputParser.getCustomerIdRange();
        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator");
        CSVInputReader csvInputReader = (CSVInputReader) ctx.getBean("CSVInputReader");
        List<Item> namePriceList = csvInputReader.parseItems("~/items.csv"/*inputParser.getItemsFile()*/);
        OutputWriter outputWriter = inputParser.getOutputWriter();
        long eventsCount = inputParser.getEventsCount();
        List<Transaction> transactions = transactionGenerator.getAllTransactions(eventsCount, namePriceList);
        if(inputParser.isOutDirDefined()) {
            String outDir = inputParser.getOutDir();
            outputWriter.createOutDir(outDir);
            outputWriter.saveToFile(transactions, outDir);
        }
    }

    public static AnnotationConfigApplicationContext createContext(CommandLinePropertySource propertySource) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.scan("logic");
        ctx.refresh();
        return ctx;
    }


    public static AnnotationConfigApplicationContext createContextWithMapPropertySource() {
        PropertiesInputParser parser = new PropertiesInputParser();
        Properties properties = parser.getProperties();
        Map<String, Object> map = new HashMap<>();
        for (final String name : properties.stringPropertyNames()) {
            logger.debug("Adding map property " + name + " => " + properties.getProperty(name));
            map.put(name, properties.getProperty(name));
        }
        MapPropertySource propertySource = new MapPropertySource("arguments", map);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.scan("logic");
        ctx.refresh();
        return ctx;
    }

    public static AnnotationConfigApplicationContext getApplicationContext() {
        return ctx;
    }
}
