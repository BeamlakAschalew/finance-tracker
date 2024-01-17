package ui;

import model.LoggedInUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface BackButtonListener {
    void onBackbuttonClicked();
}

public class TransactionsUI extends JPanel {

    JFrame parentFrame;
    JPanel parentPanel;

    private BackButtonListener backButtonListener;
    private LoggedInUser currentUser;

    public void setButtonListener(BackButtonListener listener) {
        this.backButtonListener = listener;
    }


    public TransactionsUI(JFrame mainFrame, JPanel cardLayout) {
        parentFrame = mainFrame;
        parentPanel = cardLayout;
    }

    public void setCurrentUser(LoggedInUser currentUser) {
        this.currentUser = currentUser;
    }

    public void initializeUI() {
        setLayout(new BorderLayout());
        add(new JLabel("Welcome: " + currentUser.firstName + " " + currentUser.lastName), BorderLayout.WEST);
        JButton switchToScreen1Button = new JButton("Switch to Screen 1");
        add(switchToScreen1Button, BorderLayout.EAST);

        switchToScreen1Button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        backButtonListener.onBackbuttonClicked();
                    }
                }
        );
    }
}
