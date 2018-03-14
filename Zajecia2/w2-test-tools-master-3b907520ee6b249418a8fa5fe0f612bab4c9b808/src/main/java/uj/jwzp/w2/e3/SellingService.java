package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    private final PersistenceLayer persistenceLayer;
    final CustomerMoneyService moneyService;
    final private DiscountsConfigWrapper discountsConfigWrapper;


    /* constructor used in tests */
    public SellingService(PersistenceLayer persistenceLayer, DiscountsConfigWrapper discountsConfigWrapper) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        this.discountsConfigWrapper = discountsConfigWrapper;
    }

    // constructor used in real situations ( omitted here to get as much test coverage as possible  )
//    public SellingService(PersistenceLayer persistenceLayer) {
//        this.persistenceLayer = persistenceLayer;
//        this.persistenceLayer.loadDiscountConfiguration();
//        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
//        this.discountsConfigWrapper = DiscountsConfigWrapper.SINGLETON;
//    }

    public boolean sell(Item item, int quantity, Customer customer) {
        BigDecimal money = moneyService.getMoney(customer);
        BigDecimal price = item.getPrice().subtract(discountsConfigWrapper.getDiscountForItemWrapper(item, customer)).multiply(BigDecimal.valueOf(quantity));
        if (discountsConfigWrapper.isWeekendPromotionWrapper() && price.compareTo(BigDecimal.valueOf(5)) > 0) {
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
