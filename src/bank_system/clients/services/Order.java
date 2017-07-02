package bank_system.clients.services;

import java.util.Date;

/**
 * Realization of order.
 *
 * Created on 3/24/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class Order implements java.io.Serializable {

  private static long counter = 1L;

  private long number = counter++;
  private String info;
  private long paymentDestinationNumber;
  private long paymentAmount;

  private Date creationDate;

  private boolean isPaid = false;

  public Order() {
    this(0, 0, "");
  }

  public Order(long paymentDestinationNumber, long paymentAmount, String info) {

    this.paymentDestinationNumber = paymentDestinationNumber;
    this.paymentAmount = paymentAmount;
    this.info = info;
    creationDate = new Date();

  }

  public static long getCounter() {
    return counter;
  }

  public static void setCounter(long counter) {
    Order.counter = counter;
  }

  public long getNumber() {
    return number;
  }

  public String getInfo() {
    return info;
  }

  public long getPaymentDestinationNumber() {
    return paymentDestinationNumber;
  }

  public long getPaymentAmount() {
    return paymentAmount;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  @Override
  public String toString() {
    return "Order{" +
        "number=" + number +
        ", info='" + info + '\'' +
        ", paymentDestinationNumber=" + paymentDestinationNumber +
        ", paymentAmount=" + paymentAmount +
        ", creationDate=" + creationDate +
        ", isPaid=" + isPaid +
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

    Order order = (Order) o;

    if (number != order.number) {
      return false;
    }
    if (paymentDestinationNumber != order.paymentDestinationNumber) {
      return false;
    }
    if (paymentAmount != order.paymentAmount) {
      return false;
    }
    if (isPaid != order.isPaid) {
      return false;
    }
    if (info != null ? !info.equals(order.info) : order.info != null) {
      return false;
    }
    return creationDate != null ? creationDate.equals(order.creationDate)
        : order.creationDate == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (number ^ (number >>> 32));
    result = 31 * result + (info != null ? info.hashCode() : 0);
    result = 31 * result + (int) (paymentDestinationNumber ^ (paymentDestinationNumber >>> 32));
    result = 31 * result + (int) (paymentAmount ^ (paymentAmount >>> 32));
    result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
    result = 31 * result + (isPaid ? 1 : 0);
    return result;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
