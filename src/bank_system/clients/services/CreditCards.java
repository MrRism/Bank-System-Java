package bank_system.clients.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Stores all credit cards
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class CreditCards implements java.io.Serializable {

  private Map<Long, CreditCard> creditCards;

  public CreditCards() {
    creditCards = new HashMap<>();
  }

  /*Add card from param to creditCards
  * @param CreditCard to add
  * */
  public void addCard(CreditCard creditCard) {

    removeCard(creditCard.getId());
    creditCards.put(creditCard.getId(), creditCard);

  }

  /*Remove card from creditCards by its id
    * @param id of card to remove
    * */
  public void removeCard(long id) {
    creditCards.remove(id);
  }

  /*Returns number of credit cards
    * @return int number
    * */
  public int getSize() {
    return creditCards.size();
  }

  /*Get a card by its id
  * @param id of card to get
  * @return credit card
  * */
  public CreditCard getCard(long id) {
    return creditCards.get(id);
  }

  public Collection<CreditCard> getCards() {
    return creditCards.values();
  }

  @Override
  public String toString() {
    return "CreditCards{" +
        "creditCards=" + creditCards +
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

    CreditCards that = (CreditCards) o;

    return creditCards != null ? creditCards.equals(that.creditCards) : that.creditCards == null;
  }

  @Override
  public int hashCode() {
    return creditCards != null ? creditCards.hashCode() : 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
