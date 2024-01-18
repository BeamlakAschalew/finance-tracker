package ui;

import db_controller.GetUserInfo;
import model.LoggedInUser;
import model.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


interface LoginEventListener {
    void onLoginResult(LoggedInUser result);
}


public class LoginUI extends JPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordField;

    private LoginEventListener loginEventListener;

    public void setLoginEventListener(LoginEventListener listener) {
        this.loginEventListener = listener;
    }

    JFrame parentFrame;
    JPanel parentPanel;
    CardLayout parentLayout;

    public LoginUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;
        initializeUI();
    }

    private void initializeUI() {

        parentFrame.setSize(600, 400);

        // Create components
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup instead");

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Poppins", Font.BOLD, 24));

        // Set layout manager
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

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loginEventListener.onLoginResult(performLogin(usernameTextField.getText(), new String(passwordField.getPassword())));
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parentLayout.show(parentPanel, "signupScreen");
                parentFrame.setTitle("Signup");
            }
        });
    }

    /**
     * This fetches the user info based on the username and password from the database<br>Returns `true` if user exists and `false` if the user doesn't exist or if the credentials are incorrect*/
    private LoggedInUser performLogin(String username, String password) {
        GetUserInfo gui = new GetUserInfo(username.toLowerCase(), password);
        ArrayList<UserInfo> u = gui.getUser();
        if (u.size() > 0) {
            return new LoggedInUser(u.get(0).username, u.get(0).fname, u.get(0).lname, u.get(0).email, true);
        } else {
            return new LoggedInUser(false);
        }
    }
}

