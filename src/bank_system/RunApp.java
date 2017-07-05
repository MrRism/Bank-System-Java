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
import java.io.InputStream;
import java.io.ObjectInputStream;


/**
 * Class provide application start, data load from file.
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
 * orders info fix long text
 * move file close to finally block
 *
 * To do:
 * deprecated remove
 *
 * Future improvements:
 * add support of float
 * add scrolls to lists
 * make currencies?
 * history of payments?
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
  * Method create new <code>DataStorage</code> if it wasn't read.
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

    ObjectInputStream objectInputStream = null;
    DataInputStream dataInputStream = null;
    FileInputStream fileInputStreamData = null;
    FileInputStream fileInputStreamStatic = null;

    File dataFile = new File(
        System.getProperty("user.home") + "\\AppData\\Local\\BankSystem",
        "clients.dat");
    File staticFieldsFile = new File(
        System.getProperty("user.home") + "\\AppData\\Local\\BankSystem",
        "fields.dat");

    try {

      fileInputStreamData = new FileInputStream(dataFile);
      staticFieldsFile.createNewFile();
      fileInputStreamStatic = new FileInputStream(staticFieldsFile);
      objectInputStream = new ObjectInputStream(fileInputStreamData);
      dataStorage = (DataStorage) objectInputStream.readObject();
      dataInputStream = new DataInputStream(fileInputStreamStatic);
      BankAccount.setCounter(dataInputStream.readLong());
      CreditCard.setCounter(dataInputStream.readLong());
      Order.setCounter(dataInputStream.readLong());

    } catch (IOException e) {

      dataFile.delete();

    } catch (ClassNotFoundException e1) {

      System.exit(1013);

    } finally {

      try {
        if (fileInputStreamStatic != null)
        fileInputStreamStatic.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        if (fileInputStreamData != null)
        fileInputStreamData.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        if (dataInputStream != null)
        dataInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        if (objectInputStream != null)
        objectInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }


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
