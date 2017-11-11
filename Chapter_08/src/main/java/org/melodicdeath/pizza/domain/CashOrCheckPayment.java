package org.melodicdeath.pizza.domain;


public class CashOrCheckPayment extends Payment {
  public CashOrCheckPayment() {}
  
  public String toString() {
    return "CASH or CHECK:  $" + getAmount();
  }
}
