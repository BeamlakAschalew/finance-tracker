package ui;

import db_controller.GetUserInfo;
import model.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


interface SignupEventListener {
    void onSignupResult(boolean result);
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

    public SignupUI(JFrame mainFrame, JPanel cardLayout) {
        parentFrame = mainFrame;
        parentPanel = cardLayout;
        initializeUI();
    }

    private void initializeUI() {
        parentFrame.setTitle("Signup");
        parentFrame.setSize(600, 400);

        JLabel signupLabel = new JLabel("Signup");
        signupLabel.setFont(new Font("Poppins", Font.BOLD, 24));

        usernameTextField = new JTextField(20);

        // Create components
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        passwordConfirmation = new JPasswordField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        JButton signupButton = new JButton("Signup");

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

        // Add action listener to the login button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupEventListener.onSignupResult(performLogin(usernameTextField.getText(), new String(passwordField.getPassword())));
            }
        });

        // Set Nimbus Look and Feel for a nice appearance (if available)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * This fetches the user info based on the username and password from the database<br>Returns `true` if user exists and `false` if the user doesn't exist or if the credentials are incorrect*/
    private boolean performLogin(String username, String password) {
        GetUserInfo gui = new GetUserInfo(username, password);
        ArrayList<UserInfo> u = gui.getUser();

        return u.size() != 0;
    }
}

