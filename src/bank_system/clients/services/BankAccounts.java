package bank_system.clients.services;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores all bank accounts.
 *
 * Created on 3/16/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class BankAccounts implements java.io.Serializable {

  private Map<Long, BankAccount> bankAccounts = new HashMap<>();

  /*Adds account to bank accounts.
      * @param BankAccount */
  public void addAccount(BankAccount bankAccount) {

    bankAccounts.put(bankAccount.getId(), bankAccount);

  }

  /*Annulate account and delete it
    * @param long id of account to annul */
  public void annulAccount(long id) {
    bankAccounts.remove(id);

  }

  /*Returns one bank account by its id
    * @param id of bank account to return
    * @return BankAccount
    * */
  public BankAccount getAccount(long i) {

    return bankAccounts.get(i);

  }

  public Collection<BankAccount> getAccounts() {
    return bankAccounts.values();
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
