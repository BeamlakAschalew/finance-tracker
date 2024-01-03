package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenManager {
    public void display() {
        JFrame frame = new JFrame("CardLayout Example");
        frame.setTitle("Login Page");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel loginScreen = new JPanel();
        JPanel transactionsScreen = new JPanel();

        LoginUI loginUI = new LoginUI(frame, cardPanel);
        TransactionsUI transactionsUI = new TransactionsUI(frame, cardPanel);

        loginScreen.setLayout(new BorderLayout());
        loginScreen.add(loginUI, BorderLayout.CENTER);

        transactionsScreen.add(transactionsUI);

        cardPanel.add(loginScreen, "loginScreen");
        cardPanel.add(transactionsScreen, "transactionsScreen");

        // Set the initial screen to be displayed
        cardLayout.show(cardPanel, "loginScreen");

        loginUI.setLoginEventListener(new LoginEventListener() {
            @Override
            public void onLoginResult(boolean result) {
                if (result) {
                    System.out.println("Login successful");
                    cardLayout.show(cardPanel, "transactionsScreen"); // Switch to another screen, for example
                    frame.setSize(800, 600);
                } else {
                    System.out.println("Login failed");
                    // Perform actions for unsuccessful login
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        transactionsUI.setButtonListener(new BackButtonListener() {
            @Override
            public void onBackbuttonClicked() {
                cardLayout.show(cardPanel, "loginScreen"); // Switch to another screen, for example
                frame.setSize(600, 400);
            }
        });

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
