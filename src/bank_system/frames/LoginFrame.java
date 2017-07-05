package bank_system.frames;

import static bank_system.Consts.*;

import bank_system.DataStorage;
import bank_system.clients.Client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Frame with login components, produce login and registration loading of other frames.
 *
 * Created on 3/15/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since JDK1.8
 */

public class LoginFrame extends JFrame {

  public LoginFrame() {
    this("", null);
  }

  public LoginFrame(String windowTitle, DataStorage dataStorage) {

    super(windowTitle);

    JPanel loginPanel = new JPanel();

    JButton buttonLogIn = new JButton("Log in");
    JButton buttonRegistration = new JButton("Registration");

    JTextField nameField = new JTextField("", 3);
    JPasswordField passwordField = new JPasswordField("", 3);

    JPanel loginPanelFields = new JPanel();
    JPanel loginPanelButton = new JPanel();

    loginPanel.setLayout(new BorderLayout());
    loginPanelFields.setLayout(new GridLayout(2, 2, 15, 5));

    loginPanelFields.add(new JLabel("Name  "));
    loginPanelFields.add(nameField);
    loginPanelFields.add(new JLabel("Password  "));
    loginPanelFields.add(passwordField);

    loginPanelButton.add(buttonLogIn);
    loginPanelButton.add(buttonRegistration);

    loginPanel.add(loginPanelFields, BorderLayout.CENTER);
    loginPanel.add(loginPanelButton, BorderLayout.SOUTH);

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());

    add(loginPanel, BorderLayout.CENTER);

    setVisible(true);
    setMinimumSize(new Dimension(LOGIN_FORM_WIDTH, LOGIN_FORM_HEIGHT));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    buttonLogIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        boolean isUserRegistered =
            dataStorage.getClients().getUserByName(nameField.getText()) != null;

        if (isUserRegistered) {

          boolean isEnteredUserPaswordCorrect = dataStorage.getClients()
              .getUserByName(nameField.getText()).getPassword().
                  equals(new String(passwordField.getPassword()));

          if (isEnteredUserPaswordCorrect) {

            boolean isUserAdmin = dataStorage.getClients()
                .getUserByName(nameField.getText()).isAdmin();

            if (isUserAdmin) {
              JOptionPane.showMessageDialog(null, "Welcome admin");
              new AdminFrame(dataStorage);
              dispose();
            } else {

              JOptionPane.showMessageDialog(null, "Welcome " + nameField.getText());
              new ClientFrame(dataStorage,
                  dataStorage.getClients().getUserByName(nameField.getText()));
              dispose();

            }
          } else {

            JOptionPane.showMessageDialog(null, "Invalid name and/or password");

          }

        } else {

          JOptionPane.showMessageDialog(null, "Invalid name and/or password");

        }
      }

    });

    buttonRegistration.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (nameField.getText().length() > MIN_LENGTH_PASSWORD) {

          if ((dataStorage.getClients().getUserByName(nameField.getText()) == null) ||
              (nameField.getText()).equals("admin")) {

            dataStorage.getClients().addClient(
                new Client(
                    nameField.getText(),
                    new String(passwordField.getPassword())
                ));
            JOptionPane.showMessageDialog(null, "Registration complete");
            dataStorage.saveToFile();

          } else {

            JOptionPane.showMessageDialog(null, "Name is already used");

          }
        } else {

          JOptionPane.showMessageDialog(null, "Name must be at least 4 characters");

        }
      }

    });


  }

  @Override
  public String toString() {
    return "LoginFrame{} " + super.toString();
  }
}



