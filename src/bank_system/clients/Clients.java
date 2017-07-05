package bank_system.clients;


import bank_system.clients.services.BankAccount;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.MoneyHolder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores all clients
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since  JDK1.8
 */
public class Clients implements java.io.Serializable{
  HashMap<String, Client> users = new HashMap<>();



  public Clients() {

  }
/*
* Search for client by it's name.<p>
*
* @param string name of client
* @return client
* */
  public Client getUserByName(String name){


    return users.getOrDefault(name, null);

  }

  /*
  * Add client to Clients field
  *
  * @param client to add
  * */
  public void addClient(Client client){

    users.put(client.getName(),client);


  }

  /*
  * Search for objects of MoneyHolder with same number, and returns it<p>
  *
  * @param Number of objects to search.
  * @param isDepositToCard
  * @return Credit card if it was found, null either.
  * */
  public MoneyHolder getMoneyHolderByNumber(long Number, Boolean isDepositToCard){

    if (isDepositToCard){

      for (Client client: users.values()){

        for (int i = 0 ; i<client.getCreditCardsAmount(); i++){

          if (client.getCreditCard(i).getId() == Number){

            return client.getCreditCard(i);

          }
        }
      }
    } else {
      for (Client client: users.values()){

        for (int i = 0 ; i<client.getBankAccountsAmount(); i++){

          if (client.getBankAccount(i).getId() == Number){

            return client.getBankAccount(i);

          }
        }
      }

    }

    return null;

  }

  /*
    * Generate strings with information about clients.
    *
    * @return string array with size equal to amount of clients.
    *
    * */
  public String[] getListOfUsers(){

    String[] resaut=new String[users.size()];

    int i = 0;

    for (Client client : users.values()) {

      resaut[i++]=client.getName();

    }

    return resaut;

  }
  public String[] getListOfClients(){

    ArrayList<String> resaut = new ArrayList<>();

    int i = 0;

    for (Client client : users.values()) {

      if (!client.isAdmin())resaut.add(client.getName());

    }

    return resaut.toArray(new String[0]);

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
