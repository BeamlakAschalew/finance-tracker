package ui;

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

    public void setButtonListener(BackButtonListener listener) {
        this.backButtonListener = listener;
    }

    public TransactionsUI(JFrame mainFrame, JPanel cardLayout) {
        parentFrame = mainFrame;
        parentPanel = cardLayout;
        initializeUI();

    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(new JLabel("Screen 2"), BorderLayout.NORTH);
        JButton switchToScreen1Button = new JButton("Switch to Screen 1");
        add(switchToScreen1Button);

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
