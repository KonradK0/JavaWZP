package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public class DiscountsConfigWrapper {
    public static final DiscountsConfigWrapper SINGLETON = new DiscountsConfigWrapper();


    public BigDecimal getDiscountForItemWrapper(Item item, Customer customer){
        return DiscountsConfig.getDiscountForItem(item, customer);
    }

    public boolean isWeekendPromotionWrapper(){
        return DiscountsConfig.isWeekendPromotion();
    }
}
