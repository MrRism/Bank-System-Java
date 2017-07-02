package bank_system.clients.services;


import bank_system.DataStorage;
import bank_system.clients.services.payment_exceptions.PaymentException;


/**
 * Realization of bank account.
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class BankAccount implements MoneyHolder, java.io.Serializable {

  private static long counter = 1L;

  private long id = counter++;
  private long balance;

  private DataStorage dataStorage;

  public BankAccount(DataStorage dataStorage) {
    this.dataStorage = dataStorage;
  }

  /*Returns counter
    * @return long counter*/
  public static long getCounter() {

    return counter;

  }

  /*Sets counter
  * @param long counter*/
  public static void setCounter(long counter) {

    BankAccount.counter = counter;

  }

  /*Returns value of id field
    * @return long id*/
  public long getId() {

    return id;

  }

  /*Returns value of balance field
      * @return long balance*/
  public long getBalance() {

    return balance;
  }

  /*Get a parameter and subtract it from balance
  * @param long amount
  * @throws Payment exception*/
  public void withdraw(long amount) throws PaymentException {

    if (balance > amount) {

      balance -= amount;

    } else {
      throw new PaymentException("Not enough money");
    }
  }


  /*Get a parameter and sum it from balance
  * @param long amount*/
  public void deposit(long amount) {

    balance += amount;

  }


  @Override
  public String toString() {
    return "BankAccount{" +
        "id=" + id +
        ", balance=" + balance +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BankAccount that = (BankAccount) o;

    if (id != that.id) {
      return false;
    }
    return balance == that.balance;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (balance ^ (balance >>> 32));
    return result;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
