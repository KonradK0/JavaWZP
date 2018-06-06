package logic.IOoperations;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import model.Item;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;

public class CSVInputReader {
//    public List<Item> parseItems(){
//       return getCsvToBean().parse();
//    }
//
//    private CsvToBean<Item> getCsvToBean(){
//        try {
//            InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/items.csv"));
//            return new CsvToBeanBuilder(isr)
//                    .withType(Item.class)
//                    .withSeparator(',')
//                    .build();
//        } catch (Exception e) {
//            throw new UncheckedIOException(new IOException("Failed to read from CSV"));
//        }
//    }
}
