package logic;

import logic.IOoperations.InputParser;
import logic.utils.RandomGenerator;
import logic.utils.Range;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.*;

public class TransactionGeneratorTest {

    @Mock
    InputParser inputParser;

    @Mock
    RandomGenerator randomGenerator;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void generateIdTest(){
        Mockito.when(inputParser.getCustomerIdRange()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        long id = uut.generateId();

        Assert.assertEquals(id, 7L);
    }

    @Test
    public void generateDateTest(){
        OffsetDateTime start = OffsetDateTime.of(2018,3,8,0,0,0,0, ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(2018,3,8,23,59,59,999000000, ZoneOffset.ofHours(-1));
        Mockito.when(inputParser.getDateRange()).thenReturn(new Range<>(start, end));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(start.toEpochSecond() + 3333333);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        String date = uut.generateDate();
        System.out.println(date);
        Assert.assertEquals(date, OffsetDateTime.of(LocalDateTime.ofEpochSecond(start.toEpochSecond() + 3333333, 0, start.getOffset()), start.getOffset()).toString());
    }
    @Test
    public void generateNumberOfItemsTest(){

        Mockito.when(inputParser.getItemsCountRange()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        long id = uut.generateNumberOfItems();

        Assert.assertEquals(id, 7L);
    }
    @Test
    public void generateQuantityTest(){

        Mockito.when(inputParser.getItemsQuantity()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        long id = uut.generateQuantity();

        Assert.assertEquals(id, 7L);
    }
}
