package ui;

import components.Components;
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

    CardLayout parentLayout;

    JPanel transactionPanel = new JPanel();
    JPanel top = new JPanel();
    JPanel middle = new JPanel();
    JPanel bottom = new JPanel();

    private BackButtonListener backButtonListener;
    private LoggedInUser currentUser;

    public void setButtonListener(BackButtonListener listener) {
        this.backButtonListener = listener;
    }


    public TransactionsUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;
    }

    public void setCurrentUser(LoggedInUser currentUser) {
        this.currentUser = currentUser;
    }

    public void initializeUI() {

        setLayout(new BorderLayout());
        transactionPanel.setLayout(new BorderLayout());

        top.setLayout(null);
        top.setPreferredSize(new Dimension(1250, 150));

        JLabel windowLabel = new JLabel("Transactions");
        windowLabel.setBounds(40, 20, 200, 30);
        windowLabel.setFont(new Font("Poppins", Font.BOLD, 26));

        JLabel hello = new JLabel("Welcome back: " + currentUser.username);
        hello.setBounds(40, 60, 350, 30);
        hello.setFont(new Font("Poppins", Font.PLAIN, 20));

        JLabel incomeThisMonth = new JLabel("Income this month: 1000");
        JLabel expenditureThisMonth = new JLabel("Expenditure this month: 2000");

        incomeThisMonth.setBounds(400, 60, 450, 30);
        incomeThisMonth.setFont(new Font("Poppins", Font.PLAIN, 20));
        expenditureThisMonth.setBounds(400, 100, 450, 30);
        expenditureThisMonth.setFont(new Font("Poppins", Font.PLAIN, 20));

        JButton addNew = new JButton("+ Add new transaction");
        addNew.setBounds(900, 80, 210, 40);

        JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        topSeparator.setBounds(20, 140, 1250, 5);

        top.add(hello);
        top.add(windowLabel);
        top.add(incomeThisMonth);
        top.add(expenditureThisMonth);
        top.add(addNew);
        top.add(topSeparator);
        top.setBackground(Color.GREEN);

        JPanel tableHeader = new JPanel();
        tableHeader.setLayout(null);
        tableHeader.setPreferredSize(new Dimension(1250, 65));
        tableHeader.setBackground(Color.CYAN);

        JPanel mainTable = new JPanel();
        mainTable.setPreferredSize(new Dimension(1250, 300));
        mainTable.setBackground(Color.BLUE);

        JLabel filter = new JLabel("Filter");
        filter.setBounds(30, 0, 100, 40);
        tableHeader.add(filter);

        middle.setLayout(new BorderLayout());
        middle.setPreferredSize(new Dimension(1250, 600));
        middle.add(tableHeader, BorderLayout.NORTH);
        middle.add(mainTable, BorderLayout.CENTER);
        middle.setBackground(Color.ORANGE);

        bottom.setLayout(new BorderLayout());
        bottom.setPreferredSize(new Dimension(1250, 65));
        bottom.setBackground(Color.red);


        transactionPanel.add(top, BorderLayout.NORTH);
        transactionPanel.add(middle, BorderLayout.CENTER);
        transactionPanel.add(bottom, BorderLayout.SOUTH);


//        JButton switchToScreen1Button = new JButton("Switch to Screen 1");
//        add(switchToScreen1Button, BorderLayout.EAST);
//
//
//
//        switchToScreen1Button.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent actionEvent) {
//                        backButtonListener.onBackbuttonClicked();
//                    }
//                }
//        );
        add(transactionPanel, BorderLayout.CENTER);
    }


}
