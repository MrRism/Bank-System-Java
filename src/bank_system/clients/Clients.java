package bank_system.clients;


import bank_system.clients.services.MoneyHolder;
import java.util.Collection;
import java.util.HashMap;

/**
 * Stores all clients
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class Clients implements java.io.Serializable {

  private HashMap<String, Client> users = new HashMap<>();


  public Clients() {

  }

  /*
  * Search for client by it's name.<p>
  *
  * @param string name of client
  * @return client
  * */
  public Client getUserByName(String name) throws NullPointerException {

    return users.get(name);

  }

  /*
  * Add client to Clients field
  *
  * @param client to add
  * */
  public void addClient(Client client) {

    users.put(client.getName(), client);


  }

  /*
  * Search for objects of MoneyHolder with same id, and returns it<p>
  *
  * @param id of objects to search.
  * @param DepositToCard
  * @return Credit card if it was found, null either.
  * */
  public MoneyHolder getMoneyHolderByNumber(long id, Boolean DepositToCard) {

    if (DepositToCard) {

      for (Client client : users.values()) {

        for (MoneyHolder moneyHolder : client.getListOfCreditCards()) {

          if (moneyHolder.getId() == id) {
            return moneyHolder;
          }


        }
      }
    } else {
      for (Client client : users.values()) {

        for (MoneyHolder moneyHolder : client.getListOfBankAccounts()) {

          if (moneyHolder.getId() == id) {
            return moneyHolder;
          }


        }


      }

    }

    return null;

  }

  /*
    * Returns users.
    *
    * @return Collection with all users.
    *
    * */
  public Collection<Client> getUsers() {

    return users.values();

  }


  @Override
  public String
  toString() {
    return "Clients{" +
        "users=" + users +
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

    Clients clients = (Clients) o;

    return users != null ? users.equals(clients.users) : clients.users == null;
  }

  @Override
  public int hashCode() {
    return users != null ? users.hashCode() : 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
