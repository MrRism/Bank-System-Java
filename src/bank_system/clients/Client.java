package bank_system.clients;


import bank_system.clients.services.BankAccount;
import bank_system.clients.services.BankAccounts;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.CreditCards;
import bank_system.clients.services.MoneyHolder;
import bank_system.clients.services.Order;
import bank_system.clients.services.Orders;
import bank_system.clients.services.payment_exceptions.PaymentException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides a implementation of the
 * client.
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
  public void annulAccount(long id) {
    bankAccounts.annulAccount(id);

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

    if (withdrawObject != null){

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

  /**
   * Generate the ArrayList of the <code>creditCards</code>
   * property.
   *
   * @return Collection with all bank accounts.
   */
  public Collection<MoneyHolder> getListOfBankAccounts() {

    return new ArrayList<MoneyHolder>(bankAccounts.getAccounts()); //any better solution?
  }

  /**
   * Generate the ArrayList of the <code>creditCards</code>
   * property.
   *
   * @return Collection with all credit cards.
   */

  public Collection<MoneyHolder> getListOfCreditCards() {

    return new ArrayList<MoneyHolder>(creditCards.getCards());
  }

  /**
   * Generate the strings array of the <code>creditCards</code>
   * property.
   *
   * @return array of strings. Each string have number and balance information. Amount of string
   * equals number of clients credit cards.
   */
  //!@#Edit
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
