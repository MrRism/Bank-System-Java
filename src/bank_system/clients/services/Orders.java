package bank_system.clients.services;

import java.util.ArrayList;

/**
 * Stores all orders.
 *
 * Created on 3/24/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
public class Orders implements java.io.Serializable {

  private ArrayList<Order> orders = new ArrayList<>();

  /*Adds order to orders
        * @param order */
  public void addOrder(Order order) {
    orders.add(order);
  }

  /*Returns one order  by its index
    * @param index of order to return
    * @return Order
    * */
  public Order getOrder(int index) {
    return orders.get(index);
  }

  /*Returns size of arrayList field <code>orders</code>
 * @return integer size */
  public int getOrdersAmount() {
    return orders.size();
  }

  /*Removes orders with raised flag isPayed
    * */
  public void removeCompleteOrders(){
    for (int i = 0; i<orders.size();i++) {

      if (orders.get(i).isPaid())orders.remove(i);

    }

  }

  @Override
  public String toString() {
    return "Orders{" +
        "orders=" + orders +
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

    Orders orders1 = (Orders) o;

    return orders != null ? orders.equals(orders1.orders) : orders1.orders == null;
  }

  @Override
  public int hashCode() {
    return orders != null ? orders.hashCode() : 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
