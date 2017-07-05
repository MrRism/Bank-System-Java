package bank_system.clients;


import bank_system.clients.services.BankAccount;
import bank_system.clients.services.BankAccounts;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.CreditCards;
import bank_system.clients.services.MoneyHolder;
import bank_system.clients.services.Order;
import bank_system.clients.services.Orders;
import bank_system.clients.services.payment_exceptions.PaymentException;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.org.apache.bcel.internal.generic.I2F;
import java.util.ArrayList;


/**
 * This class provides a implementation of the
 * client. For payment producing it needs reference to datastorage.
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */

public class Client implements java.io.Serializable {

  private String name;

  private String password;

  private CreditCards creditCards;

  private BankAccounts bankAccounts;

  private Orders orders;

  private boolean isAdmin;


  public Client() {
    this("", "", false);
  }
  public Client(String name, String password) {
    this(name, password, false);
  }

  public Client(String name, String password, Boolean isAdmin) {

    this.name = name + "";
    this.password = password + "";
    this.isAdmin = isAdmin;
    creditCards = new CreditCards();
    bankAccounts = new BankAccounts();
    orders = new Orders();


  }

  public boolean isAdmin() {
    return isAdmin;
  }


  public void addAccount(BankAccount bankAccount) {
    bankAccounts.addAccount(bankAccount);
  }

  /*Annulate account and delete it
    * @param integer index of account to annul */
  public void annulAccount(int index) {
    bankAccounts.annulAccount(index);

  }

  /*Adds order to orders
        * @param order */
  public void addOrder(Order order) {
    removeCompleteOrders();

    orders.addOrder(order);

  }

  /*Returns order by its index
    * @param index of order to return
    * @return Order
    * */
  public Order getOrder(int index) {
    removeCompleteOrders();

    return orders.getOrder(index);

  }

  /**
   * Generate the strings array of the <code>creditCards</code>
   * property.
   *
   * @return array of strings. Each string have number and balance information. Amount of string
   * equals number of clients credit cards.
   */
  public String[] getListOfBankAccounts() {

    String[] result = new String[bankAccounts.getSize()];

    for (int i = 0; i < bankAccounts.getSize(); i++) {

      result[i] =
          bankAccounts.getAccount(i).getId() + " (" + bankAccounts.getAccount(i).getBalance()
              + ") ";

    }

    return result;
  }

  /**
   * Generate the strings array of the <code>creditCards</code>
   * property.
   *
   * @return array of strings. Each string have number and balance information. Amount of string
   * equals number of clients credit cards.
   */

  public String[] getListOfCreditCards() {

    String[] result = new String[creditCards.getSize()];

    for (int i = 0; i < creditCards.getSize(); i++) {

      result[i] =
          creditCards.getCard(i).getId() + " (" + creditCards.getCard(i).getBalance() + ") ";

    }

    return result;
  }

  /**
   * Generate the strings array of the <code>creditCards</code>
   * property.
   *
   * @return array of strings. Each string have number and balance information. Amount of string
   * equals number of clients credit cards.
   */
  public String[] getListOfOrders() {

    removeCompleteOrders();

    ArrayList<String> result = new ArrayList<>();

    for (int i = 0; i < orders.getOrdersAmount(); i++) {

      if (!orders.getOrder(i).isPaid()) {

        result.add(
            orders.getOrder(i).getCreationDate().getDate() + "/" +
                (1 + orders.getOrder(i).getCreationDate().getMonth()) + "/" +
                (1900 + orders.getOrder(i).getCreationDate().getYear()) + " " +
                orders.getOrder(i).getCreationDate().getHours() + ":" +
                orders.getOrder(i).getCreationDate().getMinutes() + " " +
                " (" + orders.getOrder(i).getPaymentAmount() + ") ");

      }
    }

    return result.toArray(new String[0]);

  }

  /*Removes orders with raised flag isPayed
    * */
  private void removeCompleteOrders() {

    orders.removeCompleteOrders();
  }

  /*Adds credit card to cards
        * @param Credit card
        * */
  public void addCard(CreditCard creditCard) {
    creditCards.addCard(creditCard);
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public int getCreditCardsAmount() {

    return creditCards.getSize();
  }

  public int getBankAccountsAmount() {

    return bankAccounts.getSize();
  }


  public CreditCard getCreditCard(int index) {
    return creditCards.getCard(index);
  }

  public BankAccount getBankAccount(int index) {
    return bankAccounts.getAccount(index);
  }

  /*Change value isBlocked for card. From blocked card unable to withdraw.
        * @param index
         * */
  public void blockCard(int index) {

    creditCards.getCard(index).setBlocked(true);

  }


  /*Produce payment
      * @param withdraw object must implement <Code>MoneyHolder</Code>
      * @param amount of money to pay
      * @param depositObject object must implement <Code>MoneyHolder</Code>
      * @param order may be order to pay. Null if it simple transaction.
      * @throws Payment exception
      * */
  public void paymentProduce(MoneyHolder withdrawObject, long amount, MoneyHolder depositObject,
      Order order)
      throws PaymentException {

    if (withdrawObject != null)

    {
      if (depositObject != null) {

        if (depositObject.equals(withdrawObject)) {
          throw new PaymentException("Error: same destination as source");
        }

        withdrawObject.withdraw(amount);
        depositObject.deposit(amount);
        setOrderPayed(order);

      } else {

        //Simulation payment to other bank
        withdrawObject.withdraw(amount);
        setOrderPayed(order);


      }
    }
  }

  private void setOrderPayed(Order payedOrder) {
    if (payedOrder != null) {
      payedOrder.setPaid(true);
    }
  }


  @Override
  public String
  toString() {
    return "Client{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", creditCards=" + creditCards +
        ", bankAccounts=" + bankAccounts +
        ", orders=" + orders +
        ", isAdmin=" + isAdmin +
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

    Client client = (Client) o;

    if (isAdmin != client.isAdmin) {
      return false;
    }
    if (name != null ? !name.equals(client.name) : client.name != null) {
      return false;
    }
    if (password != null ? !password.equals(client.password) : client.password != null) {
      return false;
    }
    if (creditCards != null ? !creditCards.equals(client.creditCards)
        : client.creditCards != null) {
      return false;
    }
    if (bankAccounts != null ? !bankAccounts.equals(client.bankAccounts)
        : client.bankAccounts != null) {
      return false;
    }
    return orders != null ? orders.equals(client.orders) : client.orders == null;
  }


  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (creditCards != null ? creditCards.hashCode() : 0);
    result = 31 * result + (bankAccounts != null ? bankAccounts.hashCode() : 0);
    result = 31 * result + (orders != null ? orders.hashCode() : 0);
    result = 31 * result + (isAdmin ? 1 : 0);
    return result;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
