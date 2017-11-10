package org.melodicdeath.pizza.flow;

import org.apache.log4j.Logger;
import org.melodicdeath.pizza.domain.Customer;
import org.melodicdeath.pizza.service.CustomerNotFoundException;
import org.melodicdeath.pizza.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.log4j.Logger.getLogger;

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

}
