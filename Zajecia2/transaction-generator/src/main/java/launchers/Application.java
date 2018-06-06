package launchers;

import java.util.List;

import logic.IOoperations.CSVInputReader;
import logic.IOoperations.InputParser;
import logic.IOoperations.OutputWriter;
import logic.TransactionGenerator;
import logic.utils.ApplicationWrapper;
import logic.utils.RandomGenerator;
import model.Item;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

public class Application {

    private static AnnotationConfigApplicationContext ctx;

    public static void main(String... args) {
        ctx = createContext(new SimpleCommandLinePropertySource("arguments", args));
        ApplicationWrapper wrapper = (ApplicationWrapper) ctx.getBean("ApplicationWrapper");
        RandomGenerator generator = (RandomGenerator) ctx.getBean("RandomGenerator");
        InputParser inputParser = (InputParser) ctx.getBean("inputParser", wrapper, generator);
        inputParser.getCustomerIdRange();
        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator");
        CSVInputReader csvInputReader = (CSVInputReader) ctx.getBean("CSVInputReader");
        List<Item> namePriceList = csvInputReader.parseItems();
        OutputWriter outputWriter = inputParser.getOutputWriter();
        String outDir = inputParser.getOutDir();
        outputWriter.createOutDir(outDir);
        long eventsCount = inputParser.getEventsCount();
        outputWriter.saveToFile(eventsCount, outDir , transactionGenerator, namePriceList);
    }

    public static AnnotationConfigApplicationContext createContext(CommandLinePropertySource propertySource){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.scan("logic");
        ctx.refresh();
        return ctx;
    }

    public static AnnotationConfigApplicationContext getApplicationContext(){
        return ctx;
    }
}
