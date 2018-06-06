package logic.IOoperations;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import model.Item;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;

@Service("CSVInputReader")
public class CSVInputReader {
    public List<Item> parseItems(String fileName){
       return getCsvToBean(fileName).parse();
    }
    @SuppressWarnings("unchecked")
    private CsvToBean<Item> getCsvToBean(String fileName){
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("/storage/items.csv"));
            return new CsvToBeanBuilder(isr)
                    .withType(Item.class)
                    .withSeparator(',')
                    .build();
        } catch (Exception e) {
            throw new UncheckedIOException(new IOException("Failed to read from CSV"));
        }
    }
}
