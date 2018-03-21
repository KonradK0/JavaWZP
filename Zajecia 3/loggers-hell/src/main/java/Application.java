import com.external.PaymentsService;
import com.internal.DiscountCalculator;

import java.math.BigDecimal;

public class Application {

    public static void main(String[] args) {
        int ticketPrice = Integer.parseInt(args[0]);
        int customerAge = Integer.parseInt(args[1]);
        int customerId = Integer.parseInt(args[2]);
        int companyId = Integer.parseInt(args[3]);
        DiscountCalculator discountCalculator = new DiscountCalculator();
        BigDecimal actualTicketPrice = discountCalculator.calculateDiscount(new BigDecimal(ticketPrice), customerAge);
        PaymentsService paymentsService = new PaymentsService();
        paymentsService.makePayment(customerId, companyId, actualTicketPrice);
    }
}
