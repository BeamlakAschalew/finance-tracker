package ui;

import components.Components;
import db_controller.SignupUser;
import model.SignupResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupUI extends JPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmation;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;

    // Declare JFrame, JPanel and CardLayout variables, these variables will refer to those declared in the ScreenManager class
    // Since Objects are "pass by reference" it'll be easier to interact with the ScreenManager without interfaces
    JFrame parentFrame;
    JPanel parentPanel;
    CardLayout parentLayout;

    // To be called inorder to clear the user input
    void clearInputs() {
        firstNameField.setText("");
        lastNameField.setText("");
        usernameTextField.setText("");
        emailField.setText("");
        passwordConfirmation.setText("");
        passwordField.setText("");
    }

    // Receive the ScreenManager's JFrame, JPanel and CardLayout from the ScreenManager class and assign it to the variables declared above
    public SignupUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;

        // Initialize the UI as soon as an object is created
        initializeUI();
    }

    // This method displays the Login UI
    private void initializeUI() {
        // We set the frame's size and title that comes from the ScreenManager class to 600 x 400 and "Signup"
        parentFrame.setTitle("Signup");
        parentFrame.setSize(600, 400);

        // Create components
        JLabel signupLabel = new JLabel("Signup");
        signupLabel.setFont(new Font("Candara", Font.BOLD, 24));
        usernameTextField = new JTextField(20);
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        passwordConfirmation = new JPasswordField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        JButton signupButton = new JButton("Signup");
        JButton loginButton = new JButton("Login Instead");

        // Set layout manager of the JPanel of the SignupUI to GridBag
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

        // Add action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // performSignup method takes the user input and returns the SignupResponse() object with custom error codes:
                // errorCode 0 means signup successful,
                // errorCode 1 means username or email already exists,
                // errorCode 2 means there is a problem with the user input like passwords not matching etc
                // errorCode 3 means that there is a database error or some other unclear error
                SignupResponse response = performSignup(usernameTextField.getText(), new String(passwordField.getPassword()), new String(passwordConfirmation.getPassword()), firstNameField.getText(), lastNameField.getText(), emailField.getText());

                // since errorCode 0 means successful we will show success message in dialog pane and switch the screen to LoginUI after
                if (response.getErrorCode() == 0) {
                    Components.displayOptionPane("You have signed-up successfully!", 1);
                    parentLayout.show(parentPanel, "loginScreen");

                //  since errorCode 1 is username or email already existing we'll show error dialog pane
                } else if (response.getErrorCode() == 1) {
                    Components.displayOptionPane("Username or email already exists", 0);
                // since errorCode 3 is general error we'll display "Unknown error occurred"
                } else if (response.getErrorCode() == 3) {
                    Components.displayOptionPane("Unknown error occurred on signup", 0);
                }
            }
        });

        // Add event listener to the loginButton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // We will switch to the loginScreen if the user clicks login button while being in the signup screen
                parentLayout.show(parentPanel, "loginScreen");
                parentFrame.setTitle("Login");
            }
        });
    }

    // This method takes all the information necessary for account creation and validates it then performs account creation
    private SignupResponse performSignup(String username, String password, String passwordRepeat, String firstName, String lastName, String email) {
        // Check if username is less than 5 characters and show error message then return SignupResponse with errorCode 2
        if (username.length() < 5) {
            Components.displayOptionPane("Username should be at least 5 characters long", 0);
            return new SignupResponse(2);
        }

        // Check if password is less than 8 characters and show error message then return SignupResponse with errorCode 2
        if (password.length() < 8) {
            Components.displayOptionPane("Password should be at least 8 characters long", 0);
            return new SignupResponse(2);
        }

        // Check if password and passwordConformation don't match and show error message then return SignupResponse with errorCode 2
        if (passwordRepeat.compareTo(password) != 0) {
            Components.displayOptionPane("The passwords entered don't match", 0);
            return new SignupResponse(2);
        }

        // Check if first name or last name is less than 2 characters and show error message then return SignupResponse with errorCode 2
        if (firstName.length() < 2 || lastName.length() < 2) {
            Components.displayOptionPane("Name should be at least 2 characters long", 0);
            return new SignupResponse(2);
        }

        // Check if email doesn't include the '@' character
        if (!email.contains("@")) {
            Components.displayOptionPane("Enter a valid email address", 0);
            return new SignupResponse(2);
        }

        // The code will reach to this point only if the above validations are successful

        // Create a new SignupUser object with all the validated data
        SignupUser signup = new SignupUser(firstName, lastName, username.toLowerCase(), email, password);

        // invoke the checkUsernameAndEmail method, this check whether the username or email exist
        // and if it exists it will return a SignupResponse with errorCode 1
        if (signup.checkUsernameAndEmail()) {
            return new SignupResponse(1);
        }

        // At this point, all the inputs are validated and the username and email is unique
        // so we will invoke the registerUser method which will create a user in the database
        // and returns a SignupResponse with errorCode 0 if the signup is successful and returns errorCode 3 if unknown problem exists
        return signup.registerUser();
    }
}

