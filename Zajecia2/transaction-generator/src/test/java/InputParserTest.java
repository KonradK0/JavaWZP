import IOoperations.InputParser;
import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InputParserTest {

    @Mock
    CommandLine commandLine;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testConstructor(){

        InputParser inputParser = InputParser.getInstance("-customerIds", "1:20", "-dateRange", "2018-03-08T00:00:00.000-0100;2018-03-08T23:59:59.999-0100",
                "-itemsFile", "items.csv", "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./output");

        String[] toTest = new String[2];

        Assert.assertTrue(inputParser.line.hasOption("customerIds"));
        Assert.assertTrue(inputParser.line.hasOption("dateRange"));
        Assert.assertTrue(inputParser.line.hasOption("itemsFile"));
        Assert.assertTrue(inputParser.line.hasOption("itemsCount"));
        Assert.assertTrue(inputParser.line.hasOption("itemsQuantity"));
        Assert.assertTrue(inputParser.line.hasOption("eventsCount"));
        Assert.assertTrue(inputParser.line.hasOption("outDir"));

        toTest[0] = "1";
        toTest[1] = "20";
        Assert.assertArrayEquals(inputParser.line.getOptionValues("customerIds"), toTest);
        toTest[0] = "2018-03-08T00:00:00.000-0100";
        toTest[1] = "2018-03-08T23:59:59.999-0100";
        Assert.assertArrayEquals(inputParser.line.getOptionValues("dateRange"), toTest);
        Assert.assertEquals(inputParser.line.getOptionValue("itemsFile"), "items.csv");
        toTest[0] = "5";
        toTest[1] = "15";
        Assert.assertArrayEquals(inputParser.line.getOptionValues("itemsCount"), toTest);
        toTest[0] = "1";
        toTest[1] = "30";
        Assert.assertArrayEquals(inputParser.line.getOptionValues("itemsQuantity"), toTest);
        Assert.assertEquals(inputParser.line.getOptionValue("eventsCount"), "1000");
        Assert.assertEquals(inputParser.line.getOptionValue("outDir"), "./output");
    }


    @Test
    public void testGetCustomerIdRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1";
        rangeStr[1] = "60";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getCustomerIdRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 60;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testNoOptionCustomerIdRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1";
        rangeStr[1] = "20";
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getCustomerIdRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 20;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testFailGetCustomerIdRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1dsafd";
        rangeStr[1] = "20";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getCustomerIdRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 20;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testGetDateRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "2018-03-08T00:00:00.000-0100";
        rangeStr[1] = "2018-03-08T23:59:59.999-0100";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        OffsetDateTime[] range = inputParser.getDateRange();

        OffsetDateTime start = OffsetDateTime.of(2018,3,8,0,0,0,0, ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(2018,3,8,23,59,59,999000000, ZoneOffset.ofHours(-1));
        OffsetDateTime[] expected = new OffsetDateTime[2];
        expected[0] = start;
        expected[1] = end;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testFailGetDateRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "2018-03-08T00:00:00.000-0100";
        rangeStr[1] = "2018-03-08Tasgas23:59:59.999-0100";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        OffsetDateTime[] range = inputParser.getDateRange();

        OffsetDateTime start = OffsetDateTime.of(LocalDate.now(), LocalTime.of(0,0), ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(LocalDate.now(), LocalTime.of(23,59,59,999000000), ZoneOffset.ofHours(-1));
        OffsetDateTime[] expected = new OffsetDateTime[2];
        expected[0] = start;
        expected[1] = end;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testNoOptionGetDateRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "2018-03-08T00:00:00.000-0100";
        rangeStr[1] = "2018-03-08Tasgas23:59:59.999-0100";
        InputParser inputParser = new InputParser(commandLine);

        OffsetDateTime[] range = inputParser.getDateRange();

        OffsetDateTime start = OffsetDateTime.of(LocalDate.now(), LocalTime.of(0,0), ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(LocalDate.now(), LocalTime.of(23,59,59,999000000), ZoneOffset.ofHours(-1));
        OffsetDateTime[] expected = new OffsetDateTime[2];
        expected[0] = start;
        expected[1] = end;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testGetFileName(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(commandLine.getOptionValue(Mockito.any())).thenReturn("testItems.csv");

        InputParser inputParser = new InputParser(commandLine);

        Assert.assertEquals(inputParser.getItemsFile(), "testItems.csv");
    }

    @Test
    public void testNoOptionFileName(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);

        InputParser inputParser = new InputParser(commandLine);

        Assert.assertEquals(inputParser.getItemsFile(), "items.csv");
    }

    @Test
    public void testGetItemsCountRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1";
        rangeStr[1] = "20";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsCountRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 20;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testFailGetItemsCountRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1sdafds";
        rangeStr[1] = "20";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsCountRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 5;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testNoOptionItemsCountRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsCountRange();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 5;
        Assert.assertArrayEquals(range, expected);
    }

    @Test
    public void testItemsQuantityRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1";
        rangeStr[1] = "20";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsQuantity();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 20;
        Assert.assertArrayEquals(range, expected);

    }

    @Test
    public void testFailItemsQuantityRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        String[] rangeStr = new String[2];
        rangeStr[0] = "1";
        rangeStr[1] = "2gdsg0";
        Mockito.when(commandLine.getOptionValues(Mockito.any())).thenReturn(rangeStr);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsQuantity();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 30;
        Assert.assertArrayEquals(range, expected);

    }

    @Test
    public void testNoOptionItemsQuantityRange(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        InputParser inputParser = new InputParser(commandLine);

        Long[] range = inputParser.getItemsQuantity();

        Long[] expected = new Long[2];
        expected[0] = (long) 1;
        expected[1] = (long) 30;
        Assert.assertArrayEquals(range, expected);

    }

    @Test
    public void testEventsCount(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(commandLine.getOptionValue(Mockito.any())).thenReturn("300");
        InputParser inputParser = new InputParser(commandLine);
        Long out = inputParser.getEventsCount();

        Assert.assertEquals((long) out, 300);
    }

    @Test
    public void testFailEventsCount(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(commandLine.getOptionValue(Mockito.any())).thenReturn("3dasdsad00");
        InputParser inputParser = new InputParser(commandLine);
        Long out = inputParser.getEventsCount();

        Assert.assertEquals((long) out, 1000);
    }

    @Test
    public void testNoOptionEventsCount(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        InputParser inputParser = new InputParser(commandLine);
        Long out = inputParser.getEventsCount();

        Assert.assertEquals((long) out, 1000);
    }

    @Test
    public void testGetOutDir(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(commandLine.getOptionValue(Mockito.any())).thenReturn("outputDirectory");
        InputParser uut = new InputParser(commandLine);

        String out = uut.getOutDir();

        Assert.assertEquals(out, "outputDirectory");
    }

    @Test
    public void testNoOptionOutDir(){
        Mockito.when(commandLine.hasOption(Mockito.any())).thenReturn(Boolean.FALSE);
        InputParser inputParser = new InputParser(commandLine);
        String out = inputParser.getOutDir();

        Assert.assertEquals(out, "");
    }
}
