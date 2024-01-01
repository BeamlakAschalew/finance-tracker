package ui;

import db_controller.GetUserInfo;
import model.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


interface LoginEventListener {
    void onLoginResult(boolean result);
}


public class LoginFrame extends JPanel {

    private JTextField usernameTextField;
    private JPasswordField passwordField;

    private LoginEventListener loginEventListener;

    public void setLoginEventListener(LoginEventListener listener) {
        this.loginEventListener = listener;
    }

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {

        // Create components
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loginEventListener.onLoginResult(performLogin(usernameTextField.getText(), new String(passwordField.getPassword())));
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

    private boolean performLogin(String username, String password) {
        GetUserInfo gui = new GetUserInfo(username, password);
        ArrayList<UserInfo> u = gui.getUser();

        return u.size() != 0;
    }
}

