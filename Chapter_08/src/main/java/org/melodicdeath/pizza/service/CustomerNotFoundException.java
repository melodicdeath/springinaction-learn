package org.melodicdeath.pizza.service;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends Exception {
  public CustomerNotFoundException() {}
  
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
