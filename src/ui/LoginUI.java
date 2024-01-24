package ui;

import components.Components;
import db_controller.GetUserInfo;
import model.LoggedInUser;
import model.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class LoginUI extends JPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordField;

    // Declare JFrame, JPanel and CardLayout variables, these variables will refer to those declared in the ScreenManager class
    // Since Objects are "pass by reference" it'll be easier to interact with the ScreenManager without interfaces
    JFrame parentFrame;
    JPanel parentPanel;
    CardLayout parentLayout;


    // Receive the ScreenManager's JFrame, JPanel and CardLayout from the ScreenManager class and assign it to the variables declared above
    public LoginUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;

        // Initialize the UI as soon as an object is created
        initializeUI();
    }

    // This method displays the Login UI
    private void initializeUI() {

        // We set the frame's size that comes from the ScreenManager class to 600 x 400
        parentFrame.setSize(600, 400);
        parentFrame.setTitle("Login");

        // Object creation for input fields etc
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup instead");

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Poppins", Font.BOLD, 24));

        // Set layout manager of the JPanel we extended to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginLabel, gbc);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loginButton, gbc);
        gbc.gridx = 1;
        add(signupButton, gbc);

        // Create a reference to the current class
        LoginUI loginUIReference = this;


        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // performLogin method takes the user input and returns the LoggedInUser() object with full user information + boolean field isLoggedIn set to true if signed successfully, or if not it returns
                // an object with a boolean parameter set to false.
               LoggedInUser result = performLogin(usernameTextField.getText(), new String(passwordField.getPassword()));

               // Check if the user's attempt to login is successful
                if (result.isLoggedIn()) {
                    // Create a new TransactionsUI object with the same parameters as the LoginUI but the loggedIn user's info and the LoginUI class itself is passed to the TrasactionsUI
                    TransactionsUI transactionsUI = new TransactionsUI(parentFrame, parentPanel, parentLayout, result, loginUIReference);
                    System.out.println("Login successful");

                    // Add the transactionsUI created to the parent JPanel
                    parentPanel.add(transactionsUI, "transactionsScreen");

                    // Then show it
                    parentLayout.show(parentPanel, "transactionsScreen");

                    // Set the screen size to 1300 x 700
                    parentFrame.setSize(1300, 700);
                    parentFrame.setTitle("Transactions");
                    Components.centerFrameOnScreen(parentFrame);
                } else {
                    // If the login attempt is failed show a dialog box with a message
                    System.out.println("Login failed");
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add event listener to the signupButton
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Show the signup screen when it's clicked
                parentLayout.show(parentPanel, "signupScreen");
                parentFrame.setTitle("Signup");
            }
        });
    }


     // This fetches the user info based on the username and password from the database
     // Returns LoggedInUser with all the user info and a boolean field isLoggedIn set to true if user exists and LoggedInUser with a boolean field isLoggedIn set to false if the user doesn't exist or if the credentials are incorrect
    private LoggedInUser performLogin(String username, String password) {
        // Create an object of GetUserInfo with the username and password of the user input
        GetUserInfo gui = new GetUserInfo(username.toLowerCase(), password);

        // Get all the users with the username and password given by the user, it should return a single element since usernames can't be repeated in the databases
        ArrayList<UserInfo> u = gui.getUser();

        // Check if the array size is 0, it means that a user exists with that username and password
        if (u.size() > 0) {
            // Return a LoggedInUser object with all the user information
            return new LoggedInUser(u.get(0).username, u.get(0).fname, u.get(0).lname, u.get(0).email, true, u.get(0).id, password);
        } else {
            // Return LoggedInUser with the isLoggedIn parameter set to false
            return new LoggedInUser(false);
        }
    }

    // Method to be called when user navigates from the Transaction Screen back to the LoginScreen, we should reduce the screen size and set the window title to "Login"
    public void setupUI() {
        parentFrame.setSize(600, 400);
        parentFrame.setTitle("Login");
        Components.centerFrameOnScreen(parentFrame);
        clearInputs();
    }

    void clearInputs() {
        usernameTextField.setText("");
        passwordField.setText("");
    }
}

