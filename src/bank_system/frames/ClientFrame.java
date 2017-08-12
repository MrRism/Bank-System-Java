package bank_system.frames;

import static bank_system.Consts.*;

import bank_system.DataStorage;
import bank_system.clients.Client;
import bank_system.clients.services.BankAccount;
import bank_system.clients.services.CreditCard;
import bank_system.clients.services.MoneyHolder;
import bank_system.clients.services.Order;
import bank_system.clients.services.payment_exceptions.PaymentException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Frame with all client oriented magic. It's kinda cool... not really.
 *
 * Created on 3/19/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */
class ClientFrame extends JFrame {
  /**
   * This private field is for order-payment functionality.
   */
  private Order orderToPay;
  /**
   * Creates a <code>ClientFrame</code>.
   *
   * @param dataStorage the <code>DataStorage</code> that provides the access to save method.
   * @param client the <code>Client</code> that provides the current user.
   *
   */
  ClientFrame(DataStorage dataStorage, Client client) {

    super(client.getName());

    final int CHARS_IN_BUTTON = 20;

    setLayout(new BorderLayout());

    JPanel topPanel = new JPanel();

    topPanel.add(new JLabel("Current user: " + client.getName()));

    JButton logOut = new JButton("Log out");

    topPanel.add(logOut);
    add(topPanel, BorderLayout.NORTH);

    JPanel creditCardsPanel = new JPanel();
    creditCardsPanel.setLayout(new GridLayout(6, 1, 20, 20));

    JComboBox comboBoxAccounts;
    comboBoxAccounts = new JComboBox<>();
    JComboBox comboBoxCrediCards;
    comboBoxCrediCards = new JComboBox<>();
    JComboBox comboBoxSourcePayment;
    comboBoxSourcePayment = new JComboBox<>();

    JLabel creditCardNumberLable = new JLabel("Credit card number: ");
    JLabel creditCardBalanceLable = new JLabel("Credit card balance: ");
    JLabel creditCardLimitLable = new JLabel("Credit card limit: ");
    JButton blockCreditCardButton = new JButton("Block card");
    blockCreditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          MoneyHolderToComboBoxAdapter adapter = (MoneyHolderToComboBoxAdapter) comboBoxCrediCards
              .getSelectedItem();
          CreditCard creditCard = (CreditCard) adapter.getMoneyHolder();
          creditCard.setBlocked(true);
          dataStorage.saveToFile();
          refreshComboBox(comboBoxCrediCards, client.getListOfCreditCards());
          blockCreditCardButton.setVisible(false);
          creditCardNumberLable.setText("Credit card number: ");
          creditCardBalanceLable.setText("Credit card balance: ");
          creditCardLimitLable.setText("Credit card limit: ");
        } catch (ClassCastException e1) {
          JOptionPane.showMessageDialog(null, "Select a card");
        }

      }
    });

    blockCreditCardButton.setVisible(false);

    refreshComboBox(comboBoxCrediCards, client.getListOfCreditCards());

    comboBoxCrediCards.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          try {
            MoneyHolderToComboBoxAdapter adapter = (MoneyHolderToComboBoxAdapter) comboBoxCrediCards
                .getSelectedItem();
            CreditCard creditCard = (CreditCard) adapter.getMoneyHolder();
            creditCardNumberLable.setText("Credit card number: " +
                creditCard.getId());
            creditCardBalanceLable.setText("Credit card balance: " +
                creditCard.getBalance());

            if (!creditCard.isBlocked()) {
              blockCreditCardButton.setVisible(true);
              creditCardLimitLable.setText("Credit card limit: " +
                  creditCard.getCreditlimit());
            } else {
              creditCardLimitLable.setText("CARD IS BLOCKED ");
              blockCreditCardButton.setVisible(false);
            }

          } catch (ClassCastException e1) {
            blockCreditCardButton.setVisible(false);
            creditCardBalanceLable.setText("Credit card balance: ");
            creditCardNumberLable.setText("Credit card number: ");
            creditCardLimitLable.setText("Credit card limit: ");
          }
        }
      }
    });

    JButton createCreditCardButton = new JButton("Create new card");
    createCreditCardButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        client.addCard(new CreditCard(dataStorage));
        dataStorage.saveToFile();
        refreshComboBox(comboBoxCrediCards, client.getListOfCreditCards());


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

        try {
          MoneyHolderToComboBoxAdapter adapter = (MoneyHolderToComboBoxAdapter) comboBoxCrediCards
              .getSelectedItem();
          BankAccount bankAccount = (BankAccount) adapter.getMoneyHolder();
          client.annulAccount(bankAccount.getId());
          dataStorage.saveToFile();
          refreshComboBox(comboBoxAccounts, client.getListOfBankAccounts());
          annulAccountButton.setVisible(false);
          bankAccountBalanceLable.setText("Bank account balance: ");
          bankAccountNumberLable.setText("Bank account number: ");
        } catch (ClassCastException e1) {
          JOptionPane.showMessageDialog(null, "Select a account");
        }


      }
    });

    annulAccountButton.setVisible(false);

    refreshComboBox(comboBoxAccounts, client.getListOfBankAccounts());

    comboBoxAccounts.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          try {
            MoneyHolderToComboBoxAdapter adapter = (MoneyHolderToComboBoxAdapter) comboBoxAccounts
                .getSelectedItem();
            BankAccount bankAccount = (BankAccount) adapter.getMoneyHolder();
            bankAccountNumberLable.setText("Bank account number: " +
                bankAccount.getId());
            bankAccountBalanceLable.setText("Bank account balance: " +
                bankAccount.getBalance());
            annulAccountButton.setVisible(true);

          } catch (ClassCastException e1) {
            annulAccountButton.setVisible(false);
            bankAccountBalanceLable.setText("Bank account balance: ");
            bankAccountNumberLable.setText("Bank account number: ");
          }
        }
      }
    });

    JButton createAccountButton = new JButton("Create new account");
    createAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        client.addAccount(new BankAccount(dataStorage));
        refreshComboBox(comboBoxAccounts, client.getListOfBankAccounts());
        dataStorage.saveToFile();


      }
    });

    bankAccountsPanel.add(createAccountButton);

    bankAccountsPanel.add(comboBoxAccounts);
    bankAccountsPanel.add(bankAccountNumberLable);
    bankAccountsPanel.add(bankAccountBalanceLable);
    bankAccountsPanel.add(annulAccountButton);

    JPanel paymentsPanel = new JPanel();

    refreshComboBox(comboBoxSourcePayment,
        Stream
            .concat(client.getListOfBankAccounts().stream(),
                client.getListOfCreditCards().stream())
            .collect(Collectors.toList())
    );

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
    JButton paymentProduceButton = new JButton("Pay!");
    paymentsPanel.add(paymentProduceButton);
    JButton cancelOrderButton = new JButton("Cancel");
    cancelOrderButton.setVisible(false);
    paymentsPanel.add(cancelOrderButton);

    ButtonGroup group = new ButtonGroup();
    group.add(cardRB);
    group.add(accountRB);
    cardRB.setSelected(true);

    paymentProduceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        try {
          MoneyHolderToComboBoxAdapter adapter = (MoneyHolderToComboBoxAdapter) comboBoxSourcePayment
              .getSelectedItem();
          MoneyHolder paymentSource = (MoneyHolder) adapter.getMoneyHolder();

          client.paymentProduce(
              paymentSource,
              Long.parseLong(paymentAmount.getText()),
              dataStorage.getClients()
                  .getMoneyHolderByNumber(Long.parseLong(paymentDestinationField.getText()),
                      cardRB.isSelected()),
              orderToPay

          );

          paymentAmount.setText("");
          paymentDestinationField.setText("");

          comboBoxSourcePayment.setSelectedIndex(0);
          cancelOrderButton.doClick();
          refreshComboBox(comboBoxSourcePayment,
              Stream.concat(
                  client.getListOfBankAccounts().stream(),
                  client.getListOfCreditCards().stream()
              ).collect(Collectors.toList())
          );

          dataStorage.saveToFile();


        } catch (PaymentException e1) {

          JOptionPane.showMessageDialog(null, e1.getMessage());

        } catch (ClassCastException e2) {

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
    JButton orderInfoButton = new JButton();
    orderInfoButton.setBackground(new Color(0.95f, 0.95f, 0.95f));
    orderInfoButton.setHorizontalAlignment(SwingConstants.LEFT);
    centerOrdersPanel.add(orderInfoButton);
    centerOrdersPanel.add(new JLabel("Payment amount:"));
    JLabel orderPaymentLable = new JLabel();
    centerOrdersPanel.add(orderPaymentLable);
    centerOrdersPanel.add(new JLabel("Creation date:"));
    JLabel orderCreationDateLable = new JLabel();
    centerOrdersPanel.add(orderCreationDateLable);

    JButton payOrderButton = new JButton("Pay selected order");
    payOrderButton.setVisible(false);

    orderInfoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (ordersList.getSelectedIndex() > -1) {
          String message = client.getOrder(ordersList.getSelectedIndex()).getInfo();
          StringBuilder res = new StringBuilder();
          int k = 1;
          for (int i = 0; i < message.length(); i++) {
            res.append(message.charAt(i));
            if (i == CHARS_NUMBER_TO_WRAP * k) {
              k++;
              res.append("\r\n");
            }
          }

          JOptionPane.showMessageDialog(null, res);

        }
      }
    });

    ordersList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (ordersList.getSelectedIndex() > -1) {

          Order order = client.getOrder(ordersList.getSelectedIndex());

          orderNumberLable.setText("" + order.getNumber());
          int charsInButton = CHARS_IN_BUTTON;
          if (order.getInfo().length() < CHARS_IN_BUTTON) {
            charsInButton = order.getInfo().length();
          }
          orderInfoButton.setText(
              "" + order.getInfo().substring(0, charsInButton));
          orderPaymentLable
              .setText("" + order.getPaymentAmount());

          orderCreationDateLable.setText("" +
              order.getCreationDate().getDate() + "/" +
              (1 + order.getCreationDate().getMonth())
              + "/" +
              (1900 + order.getCreationDate().getYear())
              + " " +
              order.getCreationDate().getHours() + ":" +
              order.getCreationDate().getMinutes());

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
            refreshComboBox(comboBoxCrediCards, client.getListOfCreditCards());
            comboBoxCrediCards.setSelectedIndex(temp);

            break;

          case 1:

            int temp1 = comboBoxAccounts.getSelectedIndex();
            refreshComboBox(comboBoxAccounts, client.getListOfBankAccounts());
            comboBoxAccounts.setSelectedIndex(temp1);
            break;

          case 2:
            refreshComboBox(comboBoxSourcePayment,
                Stream
                    .concat(client.getListOfBankAccounts().stream(),
                        client.getListOfCreditCards().stream())
                    .collect(Collectors.toList())
            );
            break;

          case 3:

            if (client.getListOfOrders().length > 0) {

              ordersAmountLabel.setText(String.valueOf(client.getListOfOrders().length));

            } else {
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
          paymentAmount
              .setText("" + client.getOrder(ordersList.getSelectedIndex()).getPaymentAmount());
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
          orderInfoButton.setText("");
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

  /**
   * Method remove all items in comboBox and populate it with <code>MoneyHolder</code>s from collection.
   *
   * @param comboBox the <code>JComboBox</code> to refresh.
   * @param collection the <code>Collection</code> source of items for comboBox.
   *
   */
  private void refreshComboBox(JComboBox<Object> comboBox, Collection<MoneyHolder> collection) {

    if (comboBox != null) {

      comboBox.removeAllItems();
      comboBox.addItem("Choose ");
      Iterator<MoneyHolder> iterator = collection.iterator();

      while (iterator.hasNext()) {

        comboBox.addItem(new MoneyHolderToComboBoxAdapter(iterator.next()));

      }
    }
  }

  @Override
  public String toString() {
    return "ClientFrame{" +
        "orderToPay=" + orderToPay +
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

    return orderToPay != null ? orderToPay.equals(that.orderToPay) : that.orderToPay == null;
  }

  @Override
  public int hashCode() {
    return orderToPay != null ? orderToPay.hashCode() : 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  /**
   * Provide a <code>MoneyHolder></code>adaptation for population a JComboBox. It's overrides toString().
   *
   */
  private class MoneyHolderToComboBoxAdapter {

    MoneyHolder moneyHolder = null;

    MoneyHolderToComboBoxAdapter(MoneyHolder moneyHolder) {
      this.moneyHolder = moneyHolder;
    }

    MoneyHolder getMoneyHolder() {
      return moneyHolder;
    }

    @Override
    public String toString() {
      return moneyHolder.getId() +
          " (" + moneyHolder.getBalance() +
          ')';
    }
  }
}
