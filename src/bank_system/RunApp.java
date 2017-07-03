package bank_system;

import static bank_system.Consts.*;

import bank_system.clients.services.BankAccount;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.Order;
import bank_system.frames.LoginFrame;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Class provide application start, load data from file.
 *
 * Created on 3/27/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since  JDK1.8
 *
 * Done:
 * flag paid for order
 * admin to users
 * SERIALIZE and load
 * change start point
 * delete reference to login frame
 * fix static fields
 * fix flag paid
 * fix after pay action
 * refreshCB after tab change
 * interface re-work
 * hashcode fix
 * after payment update fromCB
 * order unpaying fix
 * after payment message
 * order creation messages
 * fix same payment source as destination
 *
 * To do:
 * deprecated remove
 * orders info fix long text
 *
 * Future improvements:
 * add support of float
 * move file close to finally block
 * add scrolls to lists
 * make currencies
 * history of payments
 * add limitation by size to all arrays ,fields, counters
 * MySQL
 * ...
 * PROFIT
 *
 */
public class RunApp {

  private DataStorage dataStorage = null;


  public RunApp() {

    run();

  }

  public static void main(String[] args) {

    new RunApp();


  }
  /*
  * Method create new <code>DataBase</code> if it wasn't read.
  * Creates login frame.
  *
  * */
  private void run() {

    loadFromFile();

    if (dataStorage == null) {

      dataStorage = new DataStorage();

    }
    new LoginFrame(LOGIN_WINDOW_TITLE, dataStorage);
  }
  /*
  * Method of getting data from file, either delete corrupted file.
  *
  * */
  private void loadFromFile() {

    File dataFile = new File(
        System.getProperty("user.home")+"\\AppData\\Local\\BankSystem",
        "clients.dat");

    try {

      ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(dataFile));
      dataStorage = (DataStorage) objectInputStream.readObject();
      objectInputStream.close();
      File staticFieldsFile = new File(System.getProperty("user.home")+"\\AppData\\Local\\BankSystem",
          "fields.dat");
      staticFieldsFile.createNewFile();
      DataInputStream dataOutputStream = new DataInputStream(new FileInputStream(staticFieldsFile));
      BankAccount.setCounter(dataOutputStream.readLong());
      CreditCard.setCounter(dataOutputStream.readLong());
      Order.setCounter(dataOutputStream.readLong());
      dataOutputStream.close();

    } catch (IOException e) {

      dataFile.delete();

    } catch (ClassNotFoundException e1) {

      System.exit(1013);

    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RunApp runApp = (RunApp) o;

    return dataStorage != null ? dataStorage.equals(runApp.dataStorage)
        : runApp.dataStorage == null;
  }


  @Override
  public String toString() {
    return "RunApp{" +
        "dataStorage=" + dataStorage +
        '}';
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
