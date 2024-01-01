package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationExample extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public NavigationExample() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Navigation Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create the login screen
        JPanel loginPanel = createLoginPanel();
        cardPanel.add(loginPanel, "Login");

        // Create another screen (you can add more screens as needed)
        JPanel anotherPanel = createAnotherPanel();
        cardPanel.add(anotherPanel, "Another");

        // Set up a button to switch between screens
        JButton switchButton = new JButton("Switch Screen");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardPanel); // Switch to the next card
            }
        });

        // Set up the main frame layout
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        add(switchButton, BorderLayout.SOUTH);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new FlowLayout());

        JTextField usernameTextField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameTextField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your login logic here
                JOptionPane.showMessageDialog(null, "Login Successful!");
            }
        });

        return loginPanel;
    }

    private JPanel createAnotherPanel() {
        JPanel anotherPanel = new JPanel();
        anotherPanel.add(new JLabel("This is another screen."));
        return anotherPanel;
    }


}
