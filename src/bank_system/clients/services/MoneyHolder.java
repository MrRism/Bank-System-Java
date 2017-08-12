package bank_system.clients.services;

import bank_system.clients.services.payment_exceptions.PaymentException;

/**
 * Created on 4/7/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 */
public interface MoneyHolder {

  long getId();
  long getBalance();
  void withdraw(long amount) throws PaymentException;
  void deposit(long amount);

}
