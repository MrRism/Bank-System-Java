package bank_system;

import bank_system.clients.Client;
import bank_system.clients.Clients;
import bank_system.clients.services.BankAccount;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.CreditCards;
import bank_system.clients.services.Order;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Store all accounts of users, all cards that may be blocked by admin.<p>
 *
 * Have a methods that needed. hah.
 *
 * Created on 3/15/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class DataStorage implements java.io.Serializable {

  private Clients clients = new Clients();

  private CreditCards cardsWithNegativeBalance = new CreditCards();

  public DataStorage() {

    if (clients.getListOfUsers().length < 1) {

      clients.addClient(new Client("admin", "admin", true, clients));

    }

  }

  /*
        * Returns clients from the list of clients<p>
        *
        * @return clients
        *
        * */
  public Clients getClients() {

    return clients;

  }

  /*
      * Returns credit cards from the list of card with negative balance<p>
      *
      * @return credit cards
      *
      * */
  public CreditCards getCardsWithNegativeBalance() {

    return cardsWithNegativeBalance;

  }

  /*
    * Takes credit card from the list of card with negative balance<p>
    *
    * @param index of <code>CreditCard</code> to return.
    * @return credit card
    *
    * */
  public CreditCard getCardWithNegBalance(int index) {

    return cardsWithNegativeBalance.getCard(index);

  }

  /*
  * Check for credit card in list of card with negative balance<p>
  *
  * @param <code>CreditCard</code> to search.
  * @return true if present<p>
  *   false if missing
  *
  * */
  public boolean isCardInList(CreditCard creditCard) {

    for (int i = 0; i < cardsWithNegativeBalance.getSize(); i++) {

      if (cardsWithNegativeBalance.getCard(i).equals(creditCard)) {

        return true;

      }

    }

    return false;

  }

  /*
  * Remove credit card from list of card with negative balance, do nothing if missing<p>
  *
  * @param <code>CreditCard</code> to remove.
  * */

  public void removeNegativeBalanceCard(CreditCard creditCard) {

    for (int i = 0; i < cardsWithNegativeBalance.getSize(); i++) {

      if (cardsWithNegativeBalance.getCard(i).equals(creditCard)) {

        cardsWithNegativeBalance.removeCard(i);
        break;

      }

    }

  }

  /*
    * Generate strings with information about cards with negative balance.
    *
    * @return string array with size equal to amount of card with negative balance.
    *
    * */
  public String[] getListNegativeBalanceCards() {

    String[] resault = new String[cardsWithNegativeBalance.getSize()];

    for (int i = 0; i < cardsWithNegativeBalance.getSize(); i++) {

      resault[i] = "" +
          cardsWithNegativeBalance.getCard(i).getId() + " " +
          cardsWithNegativeBalance.getCard(i).getBalance() + " " +
          cardsWithNegativeBalance.getCard(i).isBlocked();

    }

    return resault;

  }


  /*
    * Method storing data to file, either delete corrupted file. Creates directory if missing.
    *
    * */
  public void saveToFile() {

    File dataFile = new File(System.getProperty("user.home") + "\\AppData\\Local\\BankSystem",
        "clients.dat");

    try {

      File directory = new File(System.getProperty("user.home") + "\\AppData\\Local",
          "BankSystem");
      directory.mkdir();
      dataFile.createNewFile();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(
          new FileOutputStream(dataFile));
      objectOutputStream.writeObject(this);
      objectOutputStream.close();
      File staticFieldsFile = new File(
          System.getProperty("user.home") + "\\AppData\\Local\\BankSystem",
          "fields.dat");
      staticFieldsFile.createNewFile();
      DataOutputStream dataOutputStream = new DataOutputStream(
          new FileOutputStream(staticFieldsFile));
      dataOutputStream.writeLong(BankAccount.getCounter());
      dataOutputStream.writeLong(CreditCard.getCounter());
      dataOutputStream.writeLong(Order.getCounter());
      dataOutputStream.close();

    } catch (IOException e) {

      e.printStackTrace();
      System.exit(13);

    }
  }

  @Override
  public String toString() {
    return "DataStorage{" +
        "clients=" + clients +
        ", cardsWithNegativeBalance=" + cardsWithNegativeBalance +
        '}';
  }

  @Override
  public int hashCode() {

    int result = clients != null ? clients.hashCode() : 0;
    result =
        31 * result + (cardsWithNegativeBalance != null ? cardsWithNegativeBalance.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DataStorage that = (DataStorage) o;

    if (clients != null ? !clients.equals(that.clients) : that.clients != null) {
      return false;
    }
    return cardsWithNegativeBalance != null ? cardsWithNegativeBalance
        .equals(that.cardsWithNegativeBalance) : that.cardsWithNegativeBalance == null;
  }


  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
