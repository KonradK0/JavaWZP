package logic.IOoperations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.TransactionGenerator;
import model.Item;
import model.Transaction;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class JsonWriter implements OutputWriter {
    public void saveToFile(List<Transaction> transactions, String outDir) {
        createOutDir(outDir);
        for (Transaction transaction : transactions) {
            Path outPath = Paths.get(outDir, "order" + transaction.getId() + ".json");
            try (BufferedWriter writer = Files.newBufferedWriter(outPath)) {
                logger.info("Trying to save JSON file of name " + outPath.toString());
                writer.write(createJson(transaction));
            } catch (IOException e) {
                logger.warn("File " + outPath.toString() + " already exits");
            }
        }
    }

    private static String createJson(Transaction transaction) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(transaction);
    }
}
