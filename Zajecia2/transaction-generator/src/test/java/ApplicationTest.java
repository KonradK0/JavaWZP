import logic.Application;
import IOoperations.InputParser;
import logic.TransactionGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class ApplicationTest {

    @Mock
    private TransactionGenerator transactionGenerator;

    @Mock
    InputParser inputParser;

    @Mock
    ArrayList<String[]> list = new ArrayList<>();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void readFile(){
        String path = "items.csv";
        Application.readInputFile(path);
    }

    @Test
    public void malformedPathReadFile(){
        String path = "nonExistingFile";
        Application.readInputFile(path);
    }

    @Test
    public void createDir(){
        String path = "newDir";
        Application.createOutDir(path);
    }

    @Test
    public void existingDir(){
        String path = "output";
        Application.createOutDir(path);
    }

    @Test
    public void malformedPathDir(){
        String path = ".\\nonExistingDir\\newDir";
        Application.createOutDir(path);
    }

    @Test
    public void saveJsonFile(){
        Path outPath = Paths.get("output", "saveTestUnit.json");
        OffsetDateTime[] dateRange = new OffsetDateTime[2];
        dateRange[0] = OffsetDateTime.of(LocalDate.now(), LocalTime.of(0,0), ZoneOffset.UTC);
        dateRange[1] = OffsetDateTime.of(LocalDate.now(), LocalTime.of(23,59), ZoneOffset.UTC);
        Mockito.when(inputParser.getDateRange()).thenReturn(dateRange);
        Long[] customerIds = new Long[2];
        customerIds[0] = (long) 1;
        customerIds[1] = (long) 20;
        Mockito.when(inputParser.getCustomerIdRange()).thenReturn(customerIds);
        Long[] itemsCountRange = new Long[2];
        itemsCountRange[0] = (long) 1;
        itemsCountRange[1] = (long) 5;
        Mockito.when(inputParser.getItemsCountRange()).thenReturn(itemsCountRange);
        Long[] itemsQuantityRange = new Long[2];
        itemsQuantityRange[0] = (long) 1;
        itemsQuantityRange[1] = (long) 30;
        Mockito.when(inputParser.getItemsQuantity()).thenReturn(itemsQuantityRange);
        transactionGenerator = new TransactionGenerator(inputParser);
        Mockito.when(list.size()).thenReturn(1);
        String[] namePrice = new String[2];
        namePrice[0]= "item";
        namePrice[1] = "1.0";
        Mockito.when(list.get(Mockito.anyInt())).thenReturn(namePrice);
        Application.saveJsonFile(outPath, transactionGenerator, list, 0);
    }
}
