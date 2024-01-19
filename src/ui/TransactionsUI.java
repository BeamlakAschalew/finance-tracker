package ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import db_controller.GetTransaction;
import model.LoggedInUser;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    JScrollPane tableScroller;
    JButton apply = new JButton("Apply");

    private JTable transactionsTable;

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
        GetTransaction gt = new GetTransaction(currentUser.username);
        setLayout(new BorderLayout());
        transactionPanel.setLayout(new BorderLayout());

        // Create a DefaultTableModel to hold the data and column names
        Transaction tr = gt.getTransactions();
        DefaultTableModel model = new DefaultTableModel(tr.getTableData(), tr.getColumnNames());

        // Create a JTable with the DefaultTableModel
        transactionsTable = new JTable(model);
        transactionsTable.setEnabled(false);

        top.setLayout(null);
        top.setPreferredSize(new Dimension(1300, 150));

        JLabel windowLabel = new JLabel("Transactions");
        windowLabel.setBounds(40, 20, 200, 30);
        windowLabel.setFont(new Font("Candara", Font.BOLD, 26));

        JLabel hello = new JLabel("Welcome back: " + currentUser.username);
        hello.setBounds(40, 60, 350, 30);
        hello.setFont(new Font("Candara", Font.PLAIN, 20));

        JLabel incomeThisMonth = new JLabel("Income this month: 1000");
        JLabel expenditureThisMonth = new JLabel("Expenditure this month: 2000");

        incomeThisMonth.setBounds(400, 60, 450, 30);
        incomeThisMonth.setFont(new Font("Candara", Font.PLAIN, 20));
        expenditureThisMonth.setBounds(400, 100, 450, 30);
        expenditureThisMonth.setFont(new Font("Candara", Font.PLAIN, 20));

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
        tableHeader.setPreferredSize(new Dimension(1300, 45));
        tableHeader.setBackground(Color.CYAN);

        JPanel mainTable = new JPanel();
        mainTable.setLayout(null);
        mainTable.setPreferredSize(new Dimension(1300, 300));
        mainTable.setBackground(Color.BLUE);

        JLabel filter = new JLabel("Filter");
        filter.setBounds(30, 0, 50, 40);
        filter.setFont(new Font("Candara", Font.BOLD, 17));
        tableHeader.add(filter);

        JLabel from = new JLabel("From:");
        from.setBounds(100, 0, 50, 40);
        from.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(from);

        DatePickerSettings fromDateSetting = new DatePickerSettings();
        DatePicker fromDate = new DatePicker(fromDateSetting);
        fromDate.setBounds(170, 5, 200, 35);
        tableHeader.add(fromDate);

        JLabel to = new JLabel("To:");
        to.setBounds(400, 0, 40, 40);
        to.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(to);

        DatePickerSettings toDateSetting = new DatePickerSettings();
        DatePicker toDate = new DatePicker(toDateSetting);
        toDate.setBounds(460, 5, 200, 35);
        tableHeader.add(toDate);

        JLabel amount = new JLabel("Amount:");
        amount.setBounds(690, 0, 70, 40);
        amount.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(amount);

        JTextField amountField = new JTextField(6);
        amountField.setBounds(780, 5, 100, 35);
        tableHeader.add(amountField);

        JLabel type = new JLabel("Type: ");
        type.setBounds(920, 0, 40, 40);
        tableHeader.add(type);

        String[] types = new String[] {"Income", "Expenditure"};
        JComboBox<String> typeCB = new JComboBox<>(types);
        typeCB.setBounds(980, 10, 120, 25);
        typeCB.setSelectedIndex(0);
        tableHeader.add(typeCB);


        apply.setBounds(1130, 5, 110, 35);
        tableHeader.add(apply);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Selected Date: " + toDate.getDate());
            }
        });

        transactionsTable.setBounds(0, 0, 1300, 455);
        tableScroller = new JScrollPane(transactionsTable);
        tableScroller.setBounds(0,0,1250, 455);
        mainTable.add(tableScroller);

        middle.setLayout(new BorderLayout());
        middle.setPreferredSize(new Dimension(1300, 500));
        middle.add(tableHeader, BorderLayout.NORTH);
        middle.add(mainTable, BorderLayout.CENTER);
        middle.setBackground(Color.ORANGE);

        bottom.setLayout(new BorderLayout());
        bottom.setPreferredSize(new Dimension(1250, 50));
        bottom.setBackground(Color.red);


        transactionPanel.add(top, BorderLayout.NORTH);
        transactionPanel.add(middle, BorderLayout.WEST);
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
