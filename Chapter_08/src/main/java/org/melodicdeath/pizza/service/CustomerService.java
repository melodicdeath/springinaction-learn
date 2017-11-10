package org.melodicdeath.pizza.service;
import org.melodicdeath.pizza.domain.Customer;

public interface CustomerService {
   Customer lookupCustomer(String phoneNumber) throws CustomerNotFoundException;
}