package bank_system.clients.services;


import java.util.ArrayList;


/**
 * Stores all credit cards
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since   JDK1.8
 */
public class CreditCards implements java.io.Serializable{

  private ArrayList<CreditCard> creditCards;

  public CreditCards(){
     creditCards = new ArrayList<>();
  }

  /*Add card from param to creditCards
  * @param CreditCard to add
  * */
  public void addCard(CreditCard creditCard){

    delateCardIfPresent(creditCard);
    creditCards.add(creditCard);

  }
  /*Remove card from creditCards by its index
    * @param index of card to remove
    * */
  public void removeCard(int index){
    creditCards.remove(index);
  }
  /*Returns number of credit cards
    * @return int number
    * */
  public int getSize(){
    return creditCards.size();
  }

  /*Get a card by its index
  * @param index of card to get
  * @return credit card
  * */
  public CreditCard getCard(int i){
    return creditCards.get(i);
  }

  /*Remove same card from arraylist. If missing do nothing.
  * @param CreditCard to remove
  * */
  private void delateCardIfPresent(CreditCard creditCard){

    for (CreditCard card: creditCards) {

      if(card.equals(creditCard)){

        creditCards.remove(card);
        break;

      }

    }
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
