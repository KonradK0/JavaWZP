package logic.IOoperations;

import logic.TransactionGenerator;
import model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public interface OutputWriter {
    Logger logger = LogManager.getLogger(OutputWriter.class.getName());
    default void createOutDir(String outDir){
        try {
            Files.createDirectory(Paths.get(outDir));
        } catch (FileAlreadyExistsException e) {
            logger.warn("This folder already exists");
        } catch (NoSuchFileException | FileNotFoundException e) {
            logger.warn("This path is incorrect");
        } catch (IOException e) {
            logger.warn("Unidentified IOException");
        }
    }
    void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<Item> itemList);
}
