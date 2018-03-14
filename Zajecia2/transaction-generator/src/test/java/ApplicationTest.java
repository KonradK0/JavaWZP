import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class ApplicationTest {

    @Mock
    private TransactionGenerator transactionGenerator;

    @Mock InputParser inputParser;

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
        Path outPath = Paths.get("src", "test", "resources");
        String[] dateRange = new String[2];
        dateRange[0] = LocalDate.now().toString() + "T00:00:00.000-0100";
        dateRange[1] = LocalDate.now().toString() + "T23:59:59.999-0100";
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
        //Application.saveJsonFile();
    }
}
