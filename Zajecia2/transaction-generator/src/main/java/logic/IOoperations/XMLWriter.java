package logic.IOoperations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;
import logic.TransactionGenerator;
import model.Item;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class XMLWriter implements OutputWriter {

    @Override
    public void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<Item> itemList) {
        XmlMapper mapper = new XmlMapper();
        mapper.setDefaultPrettyPrinter(new DefaultXmlPrettyPrinter());
        for (int i = 0;  i < eventsCount; i++) {
            Path outPath = Paths.get(outDir, "order" + i +".xml");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outPath)){
                String xml = mapper.writeValueAsString(transactionGenerator.generateTransaction(itemList, i));
                bufferedWriter.write(xml);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}
