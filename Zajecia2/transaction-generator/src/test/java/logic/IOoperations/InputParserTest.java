package logic.IOoperations;

import launchers.Application;
import logic.IOoperations.inputParsers.InputParser;
import logic.utils.ApplicationWrapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import logic.utils.RandomGenerator;
import logic.utils.Range;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InputParserTest {

    @Mock
    private ApplicationWrapper applicationWrapper;

    @Mock
    private RandomGenerator generator;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testGetCustomerIdRange(){
        String[] args = {"--customerIds=1:30"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);
        Assert.assertEquals(uut.getCustomerIdRange(), new Range<>(1L,30L));
    }

    @Test
    public void testGetCustomerIdRangeDefault(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        Range<Long> customerIdRange = uut.getCustomerIdRange();

        Assert.assertEquals(customerIdRange, new Range<>(1L,20L));
    }


    @Test
    public void testGetDateRange(){
        String[] args = {"--dateRange=2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        Range<OffsetDateTime> range = uut.getDateRange();

        OffsetDateTime start = OffsetDateTime.of(2018,3,8,0,0,0,0, ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(2018,3,8,23,59,59,999000000, ZoneOffset.ofHours(-1));
        Range<OffsetDateTime> expected = new Range<>(start, end);
        Assert.assertEquals(range, expected);
    }

    @Test
    public void testGetDateRangeDefault(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        Range<OffsetDateTime> range = uut.getDateRange();

        OffsetDateTime start = OffsetDateTime.of(LocalDate.now(), LocalTime.of(0,0), ZoneOffset.ofHours(-1));
        OffsetDateTime end = OffsetDateTime.of(LocalDate.now(), LocalTime.of(23,59,59,999000000), ZoneOffset.ofHours(-1));
        Range<OffsetDateTime> expected = new Range<>(start, end);
        Assert.assertEquals(range, expected);
    }

    @Test
    public void testGetFileName(){
        String[] args = {"--itemsFile=testItems.csv"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);


        Assert.assertEquals(uut.getItemsFile(), "testItems.csv");
    }

    @Test
    public void testNoOptionFileName(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);


        Assert.assertEquals(uut.getItemsFile(), "storage/items.csv");
    }

    @Test
    public void testGetItemsCountRange(){
        String[] args = {"--itemsCount=5:15"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        Range<Long> range = uut.getItemsCountRange();

        Range<Long> expected = new Range<>(5L, 15L);
        Assert.assertEquals(range, expected);
    }

    @Test
    public void testItemsCountRangeDefault(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        Range<Long> range = uut.getItemsCountRange();

        Range<Long> expected = new Range<>(1L, 5L);
        Assert.assertEquals(range, expected);
    }

    @Test
    public void testItemsQuantityRange(){
        String[] args = {"--itemsQuantity=2:15"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);


        Range<Long> range = uut.getItemsQuantity();

        Range<Long> expected = new Range<>(2L, 15L);
        Assert.assertEquals(range, expected);

    }


    @Test
    public void testItemsQuantityRangeDefault(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);


        Range<Long> range = uut.getItemsQuantity();

        Range<Long> expected = new Range<>(1L, 30L);
        Assert.assertEquals(range, expected);

    }

    @Test
    public void testEventsCount(){
        String[] args = {"--eventsCount=300"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        Mockito.when(generator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(250L);
        InputParser uut = new InputParser(applicationWrapper, generator);

        long out = uut.getEventsCount();

        Assert.assertEquals(out, 250L);
    }

    @Test
    public void testEventsCountDefault(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        Mockito.when(generator.getRandomLong(Mockito.anyLong(), Mockito.anyLong())).thenReturn(999L);
        InputParser uut = new InputParser(applicationWrapper, generator);

        long out = uut.getEventsCount();

        Assert.assertEquals(out, 999L);
    }

    @Test
    public void testGetOutDir(){
        String[] args = {"--outDir=outdir"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        String out = uut.getOutDir();

        Assert.assertEquals(out, "outdir");
    }

    @Test
    public void testNoOptionOutDir(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        String out = uut.getOutDir();

        Assert.assertEquals(out, "./");
    }

    @Test
    public void testXMLOut(){
        String[] args = {"--format=xml"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        OutputWriter writer = uut.getOutputWriter();

        assert writer instanceof XMLWriter;
    }
    @Test
    public void testYamlOut(){
        String[] args = {"--format=yaml"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        OutputWriter writer = uut.getOutputWriter();

        assert writer instanceof YamlWriter;
    }
    @Test
    public void testJSONOut(){
        String[] args = {"--format=json"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        OutputWriter writer = uut.getOutputWriter();

        assert writer instanceof JsonWriter;
    }
    @Test
    public void testDefaultOut(){
        String[] args = {"--format=xmsadasl"};
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        OutputWriter writer = uut.getOutputWriter();

        assert writer instanceof JsonWriter;
    }
    @Test
    public void testOutNoOption(){
        String[] args = new String[0];
        AnnotationConfigApplicationContext ctx = Application.createContext(new SimpleCommandLinePropertySource("arguments", args));
        Mockito.when(applicationWrapper.getWrappedContext()).thenReturn(ctx);
        InputParser uut = new InputParser(applicationWrapper, generator);

        OutputWriter writer = uut.getOutputWriter();

        assert writer instanceof JsonWriter;
    }
}
