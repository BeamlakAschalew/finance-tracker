package ui;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {

   /**
    * This display method controls and runs the entire UI */
    public void display() {
        JFrame frame = new JFrame("CardLayout Example");
        frame.setTitle("Login Page");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        LoginUI loginUI = new LoginUI(frame, container);
        TransactionsUI transactionsUI = new TransactionsUI(frame, container);

        // Register the panels or in our case the screens to the card manager with a specific name
        container.add(loginUI, "loginScreen");
        container.add(transactionsUI, "transactionsScreen");

        // Set the initial screen to be displayed
        cardLayout.show(container, "transactionsScreen");

        // Listen to a login result from the login screen and navigate to transactions screen
        // if it is authenticated successfully and displaying a message if not
        loginUI.setLoginEventListener(new LoginEventListener() {
            @Override
            public void onLoginResult(boolean result) {
                if (result) {
                    System.out.println("Login successful");
                    cardLayout.show(container, "transactionsScreen"); // Switch to another screen, for example
                    frame.setSize(800, 600);
                } else {
                    System.out.println("Login failed");
                    // Perform actions for unsuccessful login
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Navigate back to the login screen
        transactionsUI.setButtonListener(new BackButtonListener() {
            @Override
            public void onBackbuttonClicked() {
                cardLayout.show(container, "loginScreen");
                frame.setSize(600, 400);
            }
        });

        frame.add(container);
        frame.setVisible(true);
    }
}
