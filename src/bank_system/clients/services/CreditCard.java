package bank_system.clients.services;

import static bank_system.Consts.*;

import bank_system.DataStorage;
import bank_system.clients.services.payment_exceptions.PaymentException;


/**
 * Realization of credit card.
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class CreditCard implements MoneyHolder, java.io.Serializable {

  private static long counter = 1L;

  private long id = counter++;
  private long balance;
  private long creditlimit = CREDIT_LIMIT;
  private boolean isBlocked = false;
  private DataStorage dataStorage;

  public CreditCard(DataStorage dataStorage) {
    this.dataStorage = dataStorage;
  }

  public long getBalance() {
    return balance;
  }

  public static void setCounter(long counter) {
    CreditCard.counter = counter;
  }

  /*Returns value of id field
      * @return long id*/
  public long getId() {
    return id;
  }

  /*Returns value of isBlocked field
      * @return true if credit card is blocked*/
  public boolean isBlocked() {
    return isBlocked;
  }

  public void setBlocked(boolean isBlocked) {
    this.isBlocked = isBlocked;
  }

  /*Get a parameter and sum it from balance
  * @param long amount*/
  public void deposit(long amount)  {

    balance += amount;
    if (balance > 0 ) {
      dataStorage.removeNegativeBalanceCard(this);
    }

  }

  public static long getCounter() {
    return counter;
  }

/*Get a parameter and subtract it from balance
 * @param long amount
 * @throws Payment exception*/
  public void withdraw(long amount) throws PaymentException {

    if (!isBlocked()) {
      if (balance + creditlimit > amount) {

        balance -= amount;

        if (balance<0){
          dataStorage.getCardsWithNegativeBalance().addCard(this);
        }


      } else {
        throw new PaymentException("Not enough money");
      }
    }
    else{
      throw new PaymentException("Card is blocked");
    }
  }



  public long getCreditlimit() {
    return creditlimit;
  }

  public void setCreditlimit(long creditlimit) {
    this.creditlimit = creditlimit;
  }

  @Override
  public String toString() {
    return "CreditCard{" +
        "id=" + id +
        ", balance=" + balance +
        ", creditlimit=" + creditlimit +
        ", isBlocked=" + isBlocked +
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

    CreditCard that = (CreditCard) o;

    if (id != that.id) {
      return false;
    }
    if (balance != that.balance) {
      return false;
    }
    if (creditlimit != that.creditlimit) {
      return false;
    }
    return isBlocked == that.isBlocked;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (balance ^ (balance >>> 32));
    result = 31 * result + (int) (creditlimit ^ (creditlimit >>> 32));
    result = 31 * result + (isBlocked ? 1 : 0);
    return result;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
