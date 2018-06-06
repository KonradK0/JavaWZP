package logic;

import logic.IOoperations.InputParser;
import logic.utils.RandomGenerator;
import logic.utils.Range;
import model.Item;
import model.Transaction;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionGeneratorTest {

    @Mock
    private InputParser inputParser;

    @Mock
    private RandomGenerator randomGenerator;

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
        String expected = OffsetDateTime.of(LocalDateTime.ofEpochSecond(start.toEpochSecond() + 3333333, 0, start.getOffset()), start.getOffset()).toString();
        Assert.assertEquals(date, expected);
    }
    @Test
    public void generateNumberOfItemsTest(){

        Mockito.when(inputParser.getItemsCountRange()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        long number = uut.generateNumberOfItems();

        Assert.assertEquals(number, 7L);
    }
    @Test
    public void generateQuantityTest(){

        Mockito.when(inputParser.getItemsQuantity()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        long quantity = uut.generateQuantity();

        Assert.assertEquals(quantity, 7L);
    }

    @Test
    public void generateTransactionTest(){
        OffsetDateTime start = OffsetDateTime.of(2018,3,8,0,0,0,0, ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(2018,3,8,23,59,59,999000000, ZoneOffset.ofHours(-1));
        Mockito.when(inputParser.getDateRange()).thenReturn(new Range<>(start, end));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(start.toEpochSecond() + 3333333);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);
        String date = uut.generateDate();
        Mockito.when(inputParser.getCustomerIdRange()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(7L);
        long custId = uut.generateId();
        Mockito.when(inputParser.getItemsCountRange()).thenReturn(new Range<>(1L, 20L));

        Transaction actual = uut.generateTransaction(null, 0);
        Transaction expected = new Transaction(0, date, custId, null, BigDecimal.ZERO);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getItemsTest(){
        Mockito.when(inputParser.getItemsQuantity()).thenReturn(new Range<>(1L, 20L));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1L);
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);
        List<Item> items = new ArrayList<>();
        items.add(new Item("jajko", 0, BigDecimal.ONE));
        Mockito.when(randomGenerator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(0L);
        List<Item> actual = uut.getItems(items, 1);
        Assert.assertEquals(actual, items);
    }

    @Test
    public void computeSumTest(){
        List<Item> items = new ArrayList<>();
        items.add(new Item("jajko", 1, BigDecimal.ONE));
        items.add(new Item("mleko", 3, BigDecimal.valueOf(2)));
        TransactionGenerator uut = new TransactionGenerator(inputParser, randomGenerator);

        BigDecimal actual = uut.computeSum(items);

        Assert.assertEquals(actual, BigDecimal.valueOf(7));
    }
}
