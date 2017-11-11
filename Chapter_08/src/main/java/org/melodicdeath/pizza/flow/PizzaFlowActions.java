package org.melodicdeath.pizza.flow;

import org.apache.log4j.Logger;
import org.melodicdeath.pizza.domain.*;
import org.melodicdeath.pizza.service.CustomerNotFoundException;
import org.melodicdeath.pizza.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.log4j.Logger.getLogger;
import static org.melodicdeath.pizza.domain.PaymentType.CREDIT_CARD;

@Component
public class PizzaFlowActions {

    @Autowired
    CustomerService customerService;

    private static final Logger LOGGER = getLogger(PizzaFlowActions.class);

    public Customer lookupCustomer(String phoneNumber)
            throws CustomerNotFoundException {
        Customer customer = customerService.lookupCustomer(phoneNumber);
        if(customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public void addCustomer(Customer customer) {
        LOGGER.warn("TODO: Flesh out the addCustomer() method.");
    }

    public boolean checkDeliveryArea(String zipCode) {
        LOGGER.warn("TODO: Flesh out the checkDeliveryArea() method.");
        return "75075".equals(zipCode);
    }

    public void saveOrder(Order order) {
        LOGGER.warn("TODO: Flesh out the saveOrder() method.");
    }

    public Payment verifyPayment(PaymentDetails paymentDetails) {
        Payment payment = null;
        if(paymentDetails.getPaymentType() == CREDIT_CARD) {
            payment = new CreditCardPayment();
        } else {
            payment = new CashOrCheckPayment();
        }

        return payment;
    }

}
