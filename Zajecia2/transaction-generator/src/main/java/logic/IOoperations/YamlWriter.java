package logic.IOoperations;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import logic.TransactionGenerator;
import model.Item;
import model.Transaction;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class YamlWriter implements OutputWriter {
    @Override
    public void saveToFile(List<Transaction> transactions, String outDir) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
        createOutDir(outDir);
        for (Transaction transaction : transactions) {
            Path outPath = Paths.get(outDir, "order" + transaction.getId() +".yaml");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outPath)){
                String xml = mapper.writeValueAsString(transaction);
                bufferedWriter.write(xml);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}
