package ui;
import components.Components;
import model.LoggedInUser;
import model.SignupResponse;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {
   /**
    * This display method controls and runs the entire UI */
    public void display() {
        JFrame frame = new JFrame();
        frame.setTitle("Home Page");

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        LoginUI loginUI = new LoginUI(frame, container, cardLayout);
        TransactionsUI transactionsUI = new TransactionsUI(frame, container, cardLayout);
        SignupUI signupUI = new SignupUI(frame, container, cardLayout);

        // Register the panels or in our case the screens to the card manager with a specific name
        container.add(loginUI, "loginScreen");
        container.add(transactionsUI, "transactionsScreen");
        container.add(signupUI, "signupScreen");

        // Set the initial screen to be displayed
        cardLayout.show(container, "signupScreen");
        frame.setSize(600, 400);
        frame.setTitle("Signup");
        Components.centerFrameOnScreen(frame);

        // Listen to a login result from the login screen and navigate to transactions screen
        // if it is authenticated successfully and displaying a message if not
        loginUI.setLoginEventListener(new LoginEventListener() {
            @Override
            public void onLoginResult(LoggedInUser result) {
                if (result.isLoggedIn()) {
                    System.out.println("Login successful");
                    transactionsUI.setCurrentUser(result);
                    transactionsUI.initializeUI();
                    cardLayout.show(container, "transactionsScreen");
                    frame.setSize(1300, 700);
                    frame.setTitle("Transactions");
                    Components.centerFrameOnScreen(frame);
                } else {
                    System.out.println("Login failed");
                    // Perform actions for unsuccessful login
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        signupUI.setSignupEventListener(new SignupEventListener() {
            @Override
            public void onSignupResult(SignupResponse result) {
                if (result.getErrorCode() == 0) {
                    cardLayout.show(container, "loginScreen");
                } else if (result.getErrorCode() == 1) {
                    Components.displayOptionPane("Username or email already exists");
                } else if (result.getErrorCode() == 3) {
                    Components.displayOptionPane("Unknown error occurred on signup");
                }
            }
        });

        // Navigate back to the login screen
        transactionsUI.setButtonListener(new BackButtonListener() {
            @Override
            public void onBackbuttonClicked() {
                Components.toggleTheme();
                cardLayout.show(container, "loginScreen");
                frame.setSize(600, 400);
                frame.setTitle("Login");
                Components.centerFrameOnScreen(frame);
            }
        });

        frame.add(container);
        frame.setVisible(true);
    }
}
