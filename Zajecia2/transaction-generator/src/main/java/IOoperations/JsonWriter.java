package IOoperations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.Application;
import logic.TransactionGenerator;
import model.Item;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonWriter implements OutputWriter {
    public void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<String[]> namePriceList) {
        for (int i = 0; i < eventsCount; i++) {
            Path outPath = Paths.get(outDir, "order" + i + ".json");
            try (BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                logger.info("Trying to save JSON file of name " + outPath.toString());
                writer.write(createJson(transactionGenerator, namePriceList, i));
            } catch (IOException e) {
                logger.warn("File " + outPath.toString() + " already exits");
            }
        }
    }

    private static String createJson(TransactionGenerator transactionGenerator, List<String[]> namePriceList, int id) {
        Transaction transaction = transactionGenerator.generateTransaction(namePriceList, id);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(transaction);
    }
}
