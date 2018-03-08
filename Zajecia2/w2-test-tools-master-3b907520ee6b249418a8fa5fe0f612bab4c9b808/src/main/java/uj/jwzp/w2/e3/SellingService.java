package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    private final PersistenceLayer persistenceLayer;
    final CustomerMoneyService moneyService;
    final private DiscountsConfigWrapper discountsConfigWrapper;


    /* constructor used in real life */
    public SellingService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        this.discountsConfigWrapper = DiscountsConfigWrapper.SINGLETON;
    }

//     constructor used in tests
//    public SellingService(PersistenceLayer persistenceLayer, DiscountsConfigWrapper dcw) {
//        this.persistenceLayer = persistenceLayer;
//        this.persistenceLayer.loadDiscountConfiguration();
//        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
//        this.discountsConfigWrapper = dcw;
//    }

    public boolean sell(Item item, int quantity, Customer customer, BigDecimal discount, Boolean isWeekendPromotion) {
        BigDecimal money = moneyService.getMoney(customer);
        BigDecimal price = item.getPrice().subtract(discount).multiply(BigDecimal.valueOf(quantity));
        if (isWeekendPromotion && price.compareTo(BigDecimal.valueOf(5)) > 0) {
            price = price.subtract(BigDecimal.valueOf(3));
        }
        boolean sold = moneyService.pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        } else {
            return sold;
        }
    }

}
