package bank_system.clients.services;

import java.util.ArrayList;

/**
 * Stores all bank accounts.
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since   JDK1.8
 */
public class BankAccounts implements java.io.Serializable{
  private  ArrayList<BankAccount> bankAccounts = new ArrayList<>();
  /*Adds account to bank accounts
      * @param BankAccount */
  public void addAccount(BankAccount bankAccount)
  {

    bankAccounts.add(bankAccount);

  }

  /*Annulate account and delete it
    * @param integer index of account to annul */
  public void annulAccount(int index)
  {
    bankAccounts.remove(index);

     }
  /*Returns size of arrayList field <code>bankAccounts</code>
 * @return integer size */
  public int getSize(){


    return bankAccounts.size();

  }

  /*Returns one bank account by its index
    * @param index of bank account to return
    * @return BankAccount
    * */
    public BankAccount getAccount(int i){

    return bankAccounts.get(i);

  }

  @Override
  public String toString() {
    return "BankAccounts{" +
        "bankAccounts=" + bankAccounts +
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

    BankAccounts that = (BankAccounts) o;

    return bankAccounts != null ? bankAccounts.equals(that.bankAccounts)
        : that.bankAccounts == null;
  }

  @Override
  public int hashCode() {
    return bankAccounts != null ? bankAccounts.hashCode() : 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
