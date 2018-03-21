import com.external.PaymentsService;
import com.internal.DiscountCalculator;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class.getName());

    public static void main(String[] args) {
        int ticketPrice = Integer.parseInt(args[0]);
        logger.info("Ticket price is " + ticketPrice);
        int customerAge = Integer.parseInt(args[1]);
        logger.info("Customer age is " + customerAge);
        int customerId = Integer.parseInt(args[2]);
        logger.info("Customer id is " + customerId);
        int companyId = Integer.parseInt(args[3]);
        logger.info("Company id is " + companyId);
        DiscountCalculator discountCalculator = new DiscountCalculator();
        BigDecimal actualTicketPrice = discountCalculator.calculateDiscount(new BigDecimal(ticketPrice), customerAge);
        PaymentsService paymentsService = new PaymentsService();
        paymentsService.makePayment(customerId, companyId, actualTicketPrice);
    }
}
