package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingServiceTest {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    private DiscountsConfigWrapper discountsConfigWrapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void notSell() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt())).thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.any())).thenReturn(new Item("i", new BigDecimal(3)));
        Mockito.when(discountsConfigWrapper.getDiscountForItemWrapper(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        Mockito.when(discountsConfigWrapper.isWeekendPromotionWrapper()).thenReturn(Boolean.FALSE);

        Item i = persistenceLayer.getItemByName("bla");
        Customer c = persistenceLayer.getCustomerById(1);

        //when
        boolean sold = uut.sell(i, 7, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellWithoutPromotion() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt())).thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.any())).thenReturn(new Item("i", new BigDecimal(3)));
        Mockito.when(discountsConfigWrapper.getDiscountForItemWrapper(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        Mockito.when(discountsConfigWrapper.isWeekendPromotionWrapper()).thenReturn(Boolean.FALSE);

        Item i = persistenceLayer.getItemByName("bla");
        Customer c = persistenceLayer.getCustomerById(1);

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellWithPromotion() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt())).thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.any())).thenReturn(new Item("i", new BigDecimal(8)));
        Mockito.when(discountsConfigWrapper.getDiscountForItemWrapper(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        Mockito.when(discountsConfigWrapper.isWeekendPromotionWrapper()).thenReturn(Boolean.TRUE);

        Item i = persistenceLayer.getItemByName("bla");
        Customer c = persistenceLayer.getCustomerById(1);

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(5), uut.moneyService.getMoney(c));
    }

    @Test
//    @PrepareForTest(DiscountsConfig.class)
    public void sellALot() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt())).thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.any())).thenReturn(new Item("i", new BigDecimal(3)));
        Mockito.when(discountsConfigWrapper.getDiscountForItemWrapper(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);
        Mockito.when(discountsConfigWrapper.isWeekendPromotionWrapper()).thenReturn(Boolean.FALSE);

        Customer c = persistenceLayer.getCustomerById(1);
        Item i = persistenceLayer.getItemByName("bla");

        uut.moneyService.addMoney(c, new BigDecimal(990));


        //when
        boolean sold = uut.sell(i, 10, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.moneyService.getMoney(c));
    }
}
