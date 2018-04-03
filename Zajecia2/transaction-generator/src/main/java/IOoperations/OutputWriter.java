package IOoperations;

import logic.Application;
import logic.TransactionGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.List;

public interface OutputWriter {
    Logger logger = LogManager.getLogger(OutputWriter.class.getName());
    void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<String[]> namePriceList);
}
