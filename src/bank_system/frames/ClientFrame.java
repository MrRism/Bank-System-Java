package bank_system.frames;

import static bank_system.Consts.*;

import bank_system.DataStorage;
import bank_system.clients.Client;

import bank_system.clients.services.BankAccount;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.Order;
import bank_system.clients.services.payment_exceptions.PaymentException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Frame with all client oriented magic. It's kinda cool when you client is only you and your mom.
 *
 * Created on 3/19/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since   JDK1.8
 */
class ClientFrame extends JFrame {

  Order orderToPay = null;
  private Client client;
  private JComboBox<String> comboBoxAccounts;
  private JComboBox<String> comboBoxCrediCards;
  private JComboBox<String> comboBoxSourcePayment;


  ClientFrame() {

  }

  ClientFrame(DataStorage dataStorage, Client client) {

    super(client.getName());

    this.client = client;

    setLayout(new BorderLayout());

    JPanel topPanel = new JPanel();

    topPanel.add(new JLabel("Curent user: " + client.getName()));

    JButton logOut = new JButton("Log out");

    topPanel.add(logOut );
    add(topPanel, BorderLayout.NORTH);

    JPanel creditCardsPanel = new JPanel();
    creditCardsPanel.setLayout(new GridLayout(6, 1,20,20));


    JLabel creditCardNumberLable = new JLabel("Credit card number: ");
    JLabel creditCardBalanceLable = new JLabel("Credit card balance: ");
    JLabel creditCardLimitLable = new JLabel("Credit card limit: ");
    JButton blockCreditCardButton = new JButton("Block card");
    blockCreditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (comboBoxCrediCards.getSelectedIndex() > 0) {
          client.blockCard(comboBoxCrediCards.getSelectedIndex() - 1);
          dataStorage.saveToFile();
          refreshCardsComboBox();
          blockCreditCardButton.setVisible(false);
          creditCardNumberLable.setText("Credit card number: ");
          creditCardBalanceLable.setText("Credit card balance: ");
          creditCardLimitLable.setText("Credit card limit: ");
        }
      }
    });

    blockCreditCardButton.setVisible(false);

    comboBoxCrediCards = new JComboBox<>();
    refreshCardsComboBox();

    comboBoxCrediCards.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (comboBoxCrediCards.getSelectedIndex() > 0) {

          creditCardNumberLable.setText("Credit card number: " +
              client.getCreditCard(comboBoxCrediCards.getSelectedIndex() - 1).getId());
          creditCardBalanceLable.setText("Credit card balance: " +
              client.getCreditCard(comboBoxCrediCards.getSelectedIndex() - 1).getBalance());

          if (!client.getCreditCard(comboBoxCrediCards.getSelectedIndex() - 1).isBlocked()) {
            blockCreditCardButton.setVisible(true);
            creditCardLimitLable.setText("Credit card limit: " +
                client.getCreditCard(comboBoxCrediCards.getSelectedIndex() - 1).getCreditlimit());
          } else {
            creditCardLimitLable.setText("CARD_IS_BLOCKED ");
            blockCreditCardButton.setVisible(false);


          }


        } else {
          blockCreditCardButton.setVisible(false);
          creditCardBalanceLable.setText("Credit card balance: ");
          creditCardNumberLable.setText("Credit card number: ");
          creditCardLimitLable.setText("Credit card limit: ");
        }
      }
    });

    JButton createCreditCardButton = new JButton("Create new card");
    createCreditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        client.addCard(new CreditCard(dataStorage));
        dataStorage.saveToFile();
        refreshCardsComboBox();


      }
    });



    creditCardsPanel.add(createCreditCardButton);
    creditCardsPanel.add(comboBoxCrediCards);
    creditCardsPanel.add(creditCardNumberLable);
    creditCardsPanel.add(creditCardBalanceLable);
    creditCardsPanel.add(creditCardLimitLable);
    creditCardsPanel.add(blockCreditCardButton);

    JPanel bankAccountsPanel = new JPanel();
    bankAccountsPanel.setLayout(new GridLayout(5, 1));

    JLabel bankAccountNumberLable = new JLabel("Bank account number: ");
    JLabel bankAccountBalanceLable = new JLabel("Bank account balance: ");
    JButton annulAccountButton = new JButton("Annul account");
    annulAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (comboBoxAccounts.getSelectedIndex() > 0) {
          client.annulAccount(comboBoxAccounts.getSelectedIndex() - 1);
          dataStorage.saveToFile();
          refreshAccountsComboBox();
          annulAccountButton.setVisible(false);
          bankAccountBalanceLable.setText("Bank account balance: ");
          bankAccountNumberLable.setText("Bank account number: ");
        }
      }
    });

    annulAccountButton.setVisible(false);

    comboBoxAccounts = new JComboBox<>();
    refreshAccountsComboBox();

    comboBoxAccounts.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (comboBoxAccounts.getSelectedIndex() > 0) {

          bankAccountNumberLable.setText("Bank account number: " +
              client.getBankAccount(comboBoxAccounts.getSelectedIndex() - 1).getId());
          bankAccountBalanceLable.setText("Bank account balance: " +
              client.getBankAccount(comboBoxAccounts.getSelectedIndex() - 1).getBalance());
          annulAccountButton.setVisible(true);
        } else {
          annulAccountButton.setVisible(false);
          bankAccountBalanceLable.setText("Bank account balance: ");
          bankAccountNumberLable.setText("Bank account number: ");
        }
      }
    });

    JButton createAccountButton = new JButton("Create new account");
    createAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        client.addAccount(new BankAccount(dataStorage));
        refreshAccountsComboBox();
        dataStorage.saveToFile();


      }
    });

    bankAccountsPanel.add(createAccountButton);

    bankAccountsPanel.add(comboBoxAccounts);
    bankAccountsPanel.add(bankAccountNumberLable);
    bankAccountsPanel.add(bankAccountBalanceLable);
    bankAccountsPanel.add(annulAccountButton);

    JPanel paymentsPanel = new JPanel();

    comboBoxSourcePayment = new JComboBox<>();
    refreshFromPaymentsComboBox();
    paymentsPanel.add(new JLabel("Pay from:"));
    paymentsPanel.add(comboBoxSourcePayment);
    paymentsPanel.add(new JLabel("Amount:"));
    JTextField paymentAmount = new JTextField("", 7);
    paymentsPanel.add(paymentAmount);
    paymentsPanel.add(new JLabel("  To:"));
    JTextField paymentDestinationField = new JTextField("", 8);
    paymentsPanel.add(paymentDestinationField);
    JRadioButton cardRB = new JRadioButton("Card");
    paymentsPanel.add(cardRB);
    JRadioButton accountRB = new JRadioButton("Account");
    paymentsPanel.add(accountRB);
    JButton paymentProduce = new JButton("Pay!");
    paymentsPanel.add(paymentProduce);
    JButton cancelOrderButton = new JButton("Cancel");
    cancelOrderButton.setVisible(false);
    paymentsPanel.add(cancelOrderButton);

    ButtonGroup group = new ButtonGroup();
    group.add(cardRB);
    group.add(accountRB);
    cardRB.setSelected(true);

    paymentProduce.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (comboBoxSourcePayment.getSelectedIndex() > 0) {
          try {

            if (cardRB.isSelected()) {

              if (comboBoxSourcePayment.getSelectedIndex() - 1 < client.getCreditCardsAmount()) {

                client.paymentProduceToCard(
                    client.getCreditCard(comboBoxSourcePayment.getSelectedIndex() - 1),
                    Long.parseLong(paymentAmount.getText()),
                    Long.parseLong(paymentDestinationField.getText())
                );
                JOptionPane.showMessageDialog(null,"Transaction successful");

              } else {

                client.paymentProduceToCard(
                    client.getBankAccount(
                    comboBoxSourcePayment.getSelectedIndex() - 1 - client.getCreditCardsAmount()),
                    Long.parseLong(paymentAmount.getText()),
                    Long.parseLong(paymentDestinationField.getText()));
                JOptionPane.showMessageDialog(null,"Transaction successful");

              }


            } else {

              if (comboBoxSourcePayment.getSelectedIndex() - 1 < client.getCreditCardsAmount()) {

                client.paymentProduceToAccount(
                    client.getCreditCard(comboBoxSourcePayment.getSelectedIndex() - 1),
                    Long.parseLong(paymentAmount.getText()),
                    Long.parseLong(paymentDestinationField.getText()),
                    orderToPay);
                orderToPay = null;
                JOptionPane.showMessageDialog(null,"Transaction successful");


              } else {

                client.paymentProduceToAccount(client.getBankAccount(
                    comboBoxSourcePayment.getSelectedIndex() - 1 - client.getCreditCardsAmount()),
                    Long.parseLong(paymentAmount.getText()),
                    Long.parseLong(paymentDestinationField.getText()),
                    orderToPay);
                orderToPay = null;
                JOptionPane.showMessageDialog(null,"Transaction successful");


              }

            }

            paymentAmount.setText("");
            paymentDestinationField.setText("");

            comboBoxSourcePayment.setSelectedIndex(0);
            cancelOrderButton.doClick();
            refreshFromPaymentsComboBox();

            dataStorage.saveToFile();


          } catch (PaymentException e1) {

            JOptionPane.showMessageDialog(null, e1.getMessage());

          }

        } else {

          JOptionPane.showMessageDialog(null, "Choose card or account");

        }

      }
    });

    JPanel ordersPanel = new JPanel();
    JPanel northOrderPanel = new JPanel();

    ordersPanel.setLayout(new BorderLayout());
    northOrderPanel.add(new JLabel("You have"));
    JLabel ordersAmountLabel = new JLabel("no");
    northOrderPanel.add(ordersAmountLabel);
    northOrderPanel.add(new JLabel("unpaid orders."));
    JPanel centerOrdersPanel = new JPanel();

    JList<String> ordersList = new JList<>(client.getListOfOrders());

    centerOrdersPanel.setLayout(new GridLayout(5, 1, 5, 5));
    centerOrdersPanel.add(northOrderPanel);
    centerOrdersPanel.add(new JLabel(""));
    centerOrdersPanel.add(new JLabel("Number:"));
    JLabel orderNumberLable = new JLabel();
    centerOrdersPanel.add(orderNumberLable);
    centerOrdersPanel.add(new JLabel("Info:"));
    JLabel orderInfoLable = new JLabel();
    centerOrdersPanel.add(orderInfoLable);
    centerOrdersPanel.add(new JLabel("Payment amount:"));
    JLabel orderPaymentLable = new JLabel();
    centerOrdersPanel.add(orderPaymentLable);
    centerOrdersPanel.add(new JLabel("Creation date:"));
    JLabel orderCreationDateLable = new JLabel();
    centerOrdersPanel.add(orderCreationDateLable);

    JButton payOrderButton = new JButton("Pay selected order");
    payOrderButton.setVisible(false);

    ordersList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (ordersList.getSelectedIndex() > -1) {

          orderNumberLable.setText("" + client.getOrder(ordersList.getSelectedIndex()).getNumber());
          orderInfoLable.setText("" + client.getOrder(ordersList.getSelectedIndex()).getInfo());
          orderPaymentLable
              .setText("" + client.getOrder(ordersList.getSelectedIndex()).getPaymentAmount());

          orderCreationDateLable.setText("" +
              client.getOrder(ordersList.getSelectedIndex()).getCreationDate().getDate() + "/" +
              (1 + client.getOrder(ordersList.getSelectedIndex()).getCreationDate().getMonth()) + "/" +
              (1900 + client.getOrder(ordersList.getSelectedIndex()).getCreationDate().getYear()) + " " +
              client.getOrder(ordersList.getSelectedIndex()).getCreationDate().getHours() + ":" +
              client.getOrder(ordersList.getSelectedIndex()).getCreationDate().getMinutes());

          payOrderButton.setVisible(true);
        }
      }
    });

    ordersPanel.add(centerOrdersPanel, BorderLayout.NORTH);
    ordersPanel.add(ordersList, BorderLayout.CENTER);
    ordersPanel.add(payOrderButton, BorderLayout.SOUTH);

    JTabbedPane tabbedPane = new JTabbedPane();

    tabbedPane.addTab("Cards ", creditCardsPanel);
    tabbedPane.addTab("Accounts ", bankAccountsPanel);
    tabbedPane.addTab("Payments ", paymentsPanel);
    tabbedPane.addTab("Orders ", ordersPanel);

    add(tabbedPane, BorderLayout.CENTER);

    cancelOrderButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        paymentAmount.setText("");
        paymentAmount.setEnabled(true);
        paymentDestinationField.setText("");
        paymentDestinationField.setEnabled(true);
        cardRB.setEnabled(true);
        accountRB.setEnabled(true);
        accountRB.setSelected(false);
        tabbedPane.setEnabled(true);
        cancelOrderButton.setVisible(false);
        orderToPay = null;

      }
    });

    tabbedPane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {

        switch (tabbedPane.getSelectedIndex()) {

          case 0:

            int temp = comboBoxCrediCards.getSelectedIndex();
            refreshCardsComboBox();
            comboBoxCrediCards.setSelectedIndex(temp);

            break;

          case 1:

            int temp1 = comboBoxAccounts.getSelectedIndex();
            refreshAccountsComboBox();
            comboBoxAccounts.setSelectedIndex(temp1);
            break;

          case 2:
            refreshFromPaymentsComboBox();
            break;

          case 3:

            if (client.getListOfOrders().length > 0) {

              ordersAmountLabel.setText(String.valueOf(client.getListOfOrders().length));

            }
            else {
              ordersAmountLabel.setText("no");
            }
            ordersList.removeAll();
            ordersList.setListData(client.getListOfOrders());
            payOrderButton.setVisible(false);

          default:
            break;
        }

      }
    });

    payOrderButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (ordersList.getSelectedIndex() > -1) {

          tabbedPane.setSelectedIndex(2);
          paymentAmount.setText("" + client.getOrder(ordersList.getSelectedIndex()).getPaymentAmount());
          paymentAmount.setEnabled(false);
          paymentDestinationField.setText(
              "" + client.getOrder(ordersList.getSelectedIndex()).getPaymentDestinationNumber());
          paymentDestinationField.setEnabled(false);
          accountRB.setSelected(true);
          cardRB.setEnabled(false);
          accountRB.setEnabled(false);
          orderToPay = client.getOrder(ordersList.getSelectedIndex());
          ordersList.setSelectedIndex(-1);
          payOrderButton.setVisible(false);
          tabbedPane.setEnabled(false);
          cancelOrderButton.setVisible(true);
          orderInfoLable.setText("");
          orderNumberLable.setText("");
          orderPaymentLable.setText("");
          orderCreationDateLable.setText("");

        }

      }
    });

    setMinimumSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    logOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        new LoginFrame(LOGIN_WINDOW_TITLE, dataStorage);
        dispose();

      }

    });

  }

  /*Update items in <code>comboBoxAccounts</code>. First item always "Choose account"*/

  private void refreshAccountsComboBox() {

    if (comboBoxAccounts != null) {

      comboBoxAccounts.removeAllItems();
      comboBoxAccounts.addItem("Choose account");

      for (String s : client.getListOfBankAccounts()) {

        comboBoxAccounts.addItem(s);

      }
    }
  }

  /*Update items in <code>comboBoxCrediCards</code>. First item always "Choose Card"*/
  private void refreshCardsComboBox() {

    if (comboBoxCrediCards != null) {

      comboBoxCrediCards.removeAllItems();
      comboBoxCrediCards.addItem("Choose Card");

      for (String s : client.getListOfCreditCards()) {

        comboBoxCrediCards.addItem(s);

      }
    }
  }

  /*Update items in <code>comboBoxSourcePayment</code>. First item always "Choose source"<p>
  * All credit cards items have prefix "Card ", bank accounts items "Account ".
  * */
  private void refreshFromPaymentsComboBox() {

    if (comboBoxSourcePayment != null) {

      comboBoxSourcePayment.removeAllItems();
      comboBoxSourcePayment.addItem("Choose source");

      for (String s : client.getListOfCreditCards()) {

        comboBoxSourcePayment.addItem("Card " + s);

      }

      for (String s : client.getListOfBankAccounts()) {

        comboBoxSourcePayment.addItem("Account " + s);

      }
    }
  }

  @Override
  public String toString() {
    return "ClientFrame{" +
        "orderToPay=" + orderToPay +
        ", client=" + client +
        ", comboBoxAccounts=" + comboBoxAccounts +
        ", comboBoxCrediCards=" + comboBoxCrediCards +
        ", comboBoxSourcePayment=" + comboBoxSourcePayment +
        "} " + super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClientFrame that = (ClientFrame) o;

    if (orderToPay != null ? !orderToPay.equals(that.orderToPay) : that.orderToPay != null) {
      return false;
    }
    if (client != null ? !client.equals(that.client) : that.client != null) {
      return false;
    }
    if (comboBoxAccounts != null ? !comboBoxAccounts.equals(that.comboBoxAccounts)
        : that.comboBoxAccounts != null) {
      return false;
    }
    if (comboBoxCrediCards != null ? !comboBoxCrediCards.equals(that.comboBoxCrediCards)
        : that.comboBoxCrediCards != null) {
      return false;
    }
    return comboBoxSourcePayment != null ? comboBoxSourcePayment.equals(that.comboBoxSourcePayment)
        : that.comboBoxSourcePayment == null;
  }

  @Override
  public int hashCode() {
    int result = orderToPay != null ? orderToPay.hashCode() : 0;
    result = 31 * result + (client != null ? client.hashCode() : 0);
    result = 31 * result + (comboBoxAccounts != null ? comboBoxAccounts.hashCode() : 0);
    result = 31 * result + (comboBoxCrediCards != null ? comboBoxCrediCards.hashCode() : 0);
    result = 31 * result + (comboBoxSourcePayment != null ? comboBoxSourcePayment.hashCode() : 0);
    return result;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
