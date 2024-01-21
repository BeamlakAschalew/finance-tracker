package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import components.Components;
import db_controller.GetTransaction;
import db_controller.InsertTransaction;
import model.LoggedInUser;
import model.Transaction;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

interface BackButtonListener {
    void onBackbuttonClicked();
}

public class TransactionsUI extends JPanel {

    JFrame parentFrame;
    JPanel parentPanel;

    CardLayout parentLayout;

    private JPanel transactionPanel = new JPanel();
    private JPanel top = new JPanel();
    private JPanel middle = new JPanel();
    private JPanel bottom = new JPanel();
    private JScrollPane tableScroller;
    private JButton apply = new JButton("Apply");
    private JButton settings = new JButton("Settings");
    private JButton logoutExit = new JButton("Logout and exit");
    private JTextField minAmountField = new JTextField(6);
    private JTextField maxAmountField = new JTextField(6);
    private JLabel incomeThisMonth = new JLabel("Income this month: 0");
    private JLabel expenditureThisMonth = new JLabel("Expenditure this month: 0");
    private DatePickerSettings toDateSetting = new DatePickerSettings();
    private DatePickerSettings fromDateSetting = new DatePickerSettings();
    private DatePicker fromDate = new DatePicker(fromDateSetting);
    private DatePicker toDate = new DatePicker(toDateSetting);
    private boolean darkTheme = false;
    private JButton toggleThemeButton = new JButton(darkTheme ? "Light theme" : "Dark theme");

    private JTable transactionsTable;

    private BackButtonListener backButtonListener;
    private LoggedInUser currentUser;
    private GetTransaction gt;
    private JPanel mainTable = new JPanel();
    private JLabel empty = new JLabel("No records found :(");
    private DefaultTableModel model;
    String[] types = new String[] {"Income", "Expenditure", "Any"};
    private JComboBox<String> typeCB = new JComboBox<>(types);
    private JButton reset = new JButton("R");


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
        gt = new GetTransaction(currentUser.username);
        setLayout(new BorderLayout());
        transactionPanel.setLayout(new BorderLayout());

        // Create a DefaultTableModel to hold the data and column names
        Transaction tr = gt.getTransactions();
        model = new DefaultTableModel(tr.getTableData(), tr.getColumnNames());
        model.fireTableDataChanged();
        countMonthly();
        //checkTableData();
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

        JPanel tableHeader = new JPanel();
        tableHeader.setLayout(null);
        tableHeader.setPreferredSize(new Dimension(1300, 45));


        mainTable.setLayout(null);
        mainTable.setPreferredSize(new Dimension(1300, 300));

        JLabel filter = new JLabel("Filter");
        filter.setBounds(10, 0, 40, 40);
        filter.setFont(new Font("Candara", Font.BOLD, 17));
        tableHeader.add(filter);

        JLabel from = new JLabel("From:");
        from.setBounds(60, 0, 50, 40);
        from.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(from);

        fromDate.setBounds(120, 5, 200, 35);
        tableHeader.add(fromDate);

        JLabel to = new JLabel("To:");
        to.setBounds(340, 0, 40, 40);
        to.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(to);

        toDate.setBounds(390, 5, 200, 35);
        tableHeader.add(toDate);

        JLabel minAmountLabel = new JLabel("Min:");
        minAmountLabel.setBounds(610, 0, 40, 40);
        minAmountLabel.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(minAmountLabel);

        minAmountField.setBounds(660, 5, 80, 35);
        tableHeader.add(minAmountField);

        JLabel maxAmountLabel = new JLabel("Max:");
        maxAmountLabel.setBounds(760, 0, 40, 40);
        maxAmountLabel.setFont(new Font("Candara", Font.PLAIN, 17));
        tableHeader.add(maxAmountLabel);

        maxAmountField.setBounds(810, 5, 80, 35);
        tableHeader.add(maxAmountField);

        JLabel type = new JLabel("Type: ");
        type.setBounds(910, 0, 40, 40);
        tableHeader.add(type);

        typeCB.setBounds(960, 10, 120, 25);
        typeCB.setSelectedIndex(0);
        tableHeader.add(typeCB);

        apply.setBounds(1100, 5, 110, 35);
        tableHeader.add(apply);

        reset.setBounds(1220, 5, 50, 35);
        tableHeader.add(reset);

        transactionsTable.setBounds(0, 0, 1300, 455);
        tableScroller = new JScrollPane(transactionsTable);
        tableScroller.setBounds(0,0,1300, 455);

        empty.setBounds(500, 150, 300, 120);
        empty.setFont(new Font("Candara", Font.BOLD, 35));

        mainTable.add(tableScroller);

        middle.setLayout(new BorderLayout());
        middle.setPreferredSize(new Dimension(1300, 500));
        middle.add(tableHeader, BorderLayout.NORTH);
        middle.add(mainTable, BorderLayout.CENTER);

        bottom.setPreferredSize(new Dimension(1250, 50));

        bottom.add(settings);
        bottom.add(logoutExit);
        bottom.add(toggleThemeButton);

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

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                applyCustomFilter();
            }
        });

        logoutExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        model.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                System.out.println("TABLE UPDATED");
                checkTableData();
            }
        });

        toggleThemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toggleTheme();
            }
        });

        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showTransactionAddWindow(parentFrame);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetTable();
            }
        });
    }

    private void applyCustomFilter() {
        double minAmount = 0;
        double maxAmount = 0;
        try {
            System.out.println(minAmountField.getText().isEmpty());
            System.out.println(maxAmountField.getText().isEmpty());
            if (minAmountField.getText() != null && !minAmountField.getText().isEmpty()) {
                minAmount = Double.parseDouble(minAmountField.getText());
            }
            if (maxAmountField.getText() != null && !maxAmountField.getText().isEmpty()) {
                maxAmount = Double.parseDouble(maxAmountField.getText());
            }
        } catch (NumberFormatException e) {
            Components.displayOptionPane("Only numbers are allowed in amount!", 0);
            return;
        }
        gt = new GetTransaction(currentUser.username);
        Transaction updatedTransactionData = gt.getTransactions(gt.generateQuery(fromDate.getDate(), toDate.getDate(), minAmount, maxAmount, String.valueOf(typeCB.getSelectedItem())));

        // Get the model from the existing table
        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();

        // Remove existing rows
        model.setRowCount(0);

        for (Object[] row : updatedTransactionData.getTableData()) {
            model.addRow(row);
        }
        model.fireTableDataChanged();
    }

    private void checkTableData() {
        System.out.println("TABLE UPDATED");
        if (model.getRowCount() >= 1) {
            Component[] components = mainTable.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i].equals(empty)) {
                    mainTable.remove(i);
                }
            }
            mainTable.revalidate();
            mainTable.add(tableScroller);
            mainTable.repaint();
        } else {
            Component[] components = mainTable.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i].equals(tableScroller)) {
                    mainTable.remove(i);
                }
            }
            mainTable.revalidate();
            mainTable.add(empty);
            mainTable.repaint();
        }
    }

    private void countMonthly() {
        double exp = 0;
        double inc = 0;
        String type = "";
        int rows = model.getRowCount();
        for (int i = 1; i < rows; i++) {
            type = String.valueOf(model.getValueAt(i, 3));
            if (type.equals("Income")) {
                inc = inc + Double.parseDouble(model.getValueAt(i, 1).toString());
            } else {
                exp = exp + Double.parseDouble(model.getValueAt(i, 1).toString());
            }

        }
        incomeThisMonth.setText("Total income: " + inc);
        expenditureThisMonth.setText("Total expenditure: " + exp);
    }

    private void toggleTheme() {
        try {
            if (darkTheme) {
                toggleThemeButton.setText("Dark theme");
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                toggleThemeButton.setText("Light theme");
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }
            FlatLaf.updateUI();
            darkTheme = !darkTheme;
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


    private void showTransactionAddWindow(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Add transaction", false);
        dialog.setSize(500, 500);
        JPanel transactionInputPanel = new JPanel();
        JTextField amount = new JTextField(10);
        JLabel amountText = new JLabel("Amount:");
        JComboBox<String> typeCB = new JComboBox<>(new String[]{"Income", "Expenditure"});
        JLabel typeText = new JLabel("Type:");
        JComboBox<String> categoryCB = new JComboBox<>(new String[]{"Bill", "School", "Recreation"});
        JLabel categoryText = new JLabel("Category:");
        DatePickerSettings transactionDateSetting = new DatePickerSettings();
        DatePicker transactionDate = new DatePicker(transactionDateSetting);
        JLabel dateText = new JLabel("Date:");
        JButton addButton = new JButton("+ ADD");
        JLabel notesText = new JLabel("Notes:");
        JTextArea notes = new JTextArea(5, 15);

        transactionInputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        transactionInputPanel.add(new JLabel("Fill in the inputs to add a new transaction:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        transactionInputPanel.add(amountText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        transactionInputPanel.add(amount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        transactionInputPanel.add(typeText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        transactionInputPanel.add(typeCB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        transactionInputPanel.add(categoryText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        transactionInputPanel.add(categoryCB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        transactionInputPanel.add(dateText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        transactionInputPanel.add(transactionDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        transactionInputPanel.add(notesText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        transactionInputPanel.add(new JScrollPane(notes), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        transactionInputPanel.add(addButton, gbc);

        dialog.add(transactionInputPanel);
        Components.centerDialogOnScreen(dialog);
        dialog.setResizable(false);
        dialog.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("CLICKED 1");
                System.out.println(String.valueOf(typeCB.getSelectedItem()));
                System.out.println("N: " + notes.getText());
                if (addTransactionIntoDB(amount.getText(), String.valueOf(typeCB.getSelectedItem()), categoryCB.getSelectedIndex() + 1, transactionDate.getDate(), notes.getText())) {
                    Components.displayOptionPane("Transaction added successfully!", 1);
                    dialog.setVisible(false);
                } else {
                    Components.displayOptionPane("Transaction added failed!", 0);
                }

            }
        });
    }

    boolean addTransactionIntoDB(String amount, String type, int category, LocalDate date, String notes) {
        double trAmount = 0;
        try {
            if (amount != null && !amount.isEmpty()) {
                trAmount = Double.parseDouble(amount);
                if (trAmount == 0) {
                    Components.displayOptionPane("You don't want to add 0 do you?", 0);
                    return false;
                }
            } else {
                Components.displayOptionPane("Please enter amount", 0);
                return false;
            }
        } catch (NumberFormatException e) {
            Components.displayOptionPane("Only numbers are allowed in amount field!", 0);
            return false;
        }

        InsertTransaction addTr = new InsertTransaction(currentUser.username);
        return addTr.addTransaction(addTr.generateQuery(trAmount, type, category, date, notes, currentUser.id));
    }

    private void resetTable() {
        minAmountField.setText("");
        maxAmountField.setText("");
        fromDate.setText("");
        toDate.setText("");
        typeCB.setSelectedIndex(0);

        Transaction updatedTransactionData = gt.getTransactions();
        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();
        model.setRowCount(0);

        for (Object[] row : updatedTransactionData.getTableData()) {
            model.addRow(row);
        }
        model.fireTableDataChanged();
    }
}
