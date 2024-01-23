package ui;

import components.Components;
import db_controller.SignupUser;
import model.SignupResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


interface SignupEventListener {
    void onSignupResult(SignupResponse result);
}


public class SignupUI extends JPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmation;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;

    private SignupEventListener signupEventListener;

    public void setSignupEventListener(SignupEventListener listener) {
        this.signupEventListener = listener;
    }

    JFrame parentFrame;
    JPanel parentPanel;
    CardLayout parentLayout;


    void clearInputs() {
        firstNameField.setText("");
        lastNameField.setText("");
        usernameTextField.setText("");
        emailField.setText("");
        passwordConfirmation.setText("");
        passwordField.setText("");
    }

    public SignupUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;
        initializeUI();
    }

    private void initializeUI() {
        parentFrame.setTitle("Signup");
        parentFrame.setSize(600, 400);

        JLabel signupLabel = new JLabel("Signup");
        signupLabel.setFont(new Font("Candara", Font.BOLD, 24));

        usernameTextField = new JTextField(20);

        // Create components
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        passwordConfirmation = new JPasswordField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        JButton signupButton = new JButton("Signup");
        JButton loginButton = new JButton("Login Instead");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(signupLabel, gbc);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("First name:"), gbc);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Last name:"), gbc);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Repeat password:"), gbc);
        gbc.gridx = 1;
        add(passwordConfirmation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(signupButton, gbc);

        gbc.gridx = 2;
        add(loginButton, gbc);

        // Add action listener to the login button
        signupButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                signupEventListener.
                        onSignupResult(performSignup(usernameTextField.getText(), new String(passwordField.getPassword()), new String(passwordConfirmation.getPassword()), firstNameField.getText(), lastNameField.getText(), emailField.getText()));
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parentLayout.show(parentPanel, "loginScreen");
                parentFrame.setTitle("Login");
            }
        });
    }

    /**
     * This fetches the user info based on the username and password from the database<br>Returns `true` if user exists and `false` if the user doesn't exist or if the credentials are incorrect*/
    private SignupResponse performSignup(String username, String password, String passwordRepeat, String firstName, String lastName, String email) {

        if (username.length() < 5) {
            Components.displayOptionPane("Username should be at least 5 characters long", 0);
            return new SignupResponse(2);
        }
        if (password.length() < 8) {
            Components.displayOptionPane("Password should be at least 8 characters long", 0);
            return new SignupResponse(2);
        }
        if (passwordRepeat.compareTo(password) != 0) {
            Components.displayOptionPane("The passwords entered don't match", 0);
            return new SignupResponse(2);
        }

        if (firstName.length() < 2 || lastName.length() < 2) {
            Components.displayOptionPane("Name should be at least 2 characters long", 0);
            return new SignupResponse(2);
        }

        if (!email.contains("@")) {
            Components.displayOptionPane("Enter a valid email address", 0);
            return new SignupResponse(2);
        }

        SignupUser signup = new SignupUser(firstName, lastName, username.toLowerCase(), email, password);

        if (signup.checkUsernameAndEmail()) {
            return new SignupResponse(1);
        }


        return signup.registerUser();
    }
}

