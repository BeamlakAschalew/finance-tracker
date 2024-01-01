package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenManager {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;


    public void display() {
        JFrame frame = new JFrame("CardLayout Example");
        frame.setTitle("Login Page");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Screen 1
        JPanel screen1 = new JPanel();
        screen1.setLayout(new BorderLayout());
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setLoginEventListener(new LoginEventListener() {
            @Override
            public void onLoginResult(boolean result) {
                if (result) {
                    System.out.println("Login successful");
                    cardLayout.show(cardPanel, "screen2"); // Switch to another screen, for example
                } else {
                    System.out.println("Login failed");
                    // Perform actions for unsuccessful login
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        screen1.add(loginFrame, BorderLayout.CENTER);

        // Screen 2
        JPanel screen2 = new JPanel();
        screen2.add(new JLabel("Screen 2"));
        JButton switchToScreen1Button = new JButton("Switch to Screen 1");
        switchToScreen1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        screen2.add(switchToScreen1Button);

        // Add screens to the cardPanel
        cardPanel.add(screen1, "screen1");
        cardPanel.add(screen2, "screen2");

        // Set the initial screen to be displayed
        cardLayout.show(cardPanel, "screen1");

        frame.add(cardPanel);
        frame.setVisible(true);
    }


}
