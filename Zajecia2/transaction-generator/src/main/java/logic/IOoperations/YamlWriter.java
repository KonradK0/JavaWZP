package logic.IOoperations;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import logic.TransactionGenerator;
import model.Item;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class YamlWriter implements OutputWriter {
    @Override
    public void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<Item> itemList) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
        for (int i = 0;  i < eventsCount; i++) {
            Path outPath = Paths.get(outDir, "order" + i +".yaml");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outPath)){
                String xml = mapper.writeValueAsString(transactionGenerator.generateTransaction(itemList, i));
                bufferedWriter.write(xml);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}
