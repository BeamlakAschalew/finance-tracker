package ui;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import components.Components;
import db_controller.GetTransaction;
import db_controller.InsertTransaction;
import db_controller.SignupUser;
import db_controller.UpdateTransaction;
import model.LoggedInUser;
import model.Transaction;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class TransactionsUI extends JPanel {

    // Declare JFrame, JPanel and CardLayout variables, these variables will refer to those declared in the ScreenManager class
    // Since Objects are "pass by reference" it'll be easier to interact with the ScreenManager without interfaces
    JFrame parentFrame;
    JPanel parentPanel;
    CardLayout parentLayout;

    // Create a panel that holds all other components that is going to be added in the JPanel this class extended
    private JPanel transactionPanel = new JPanel();
    private JPanel top = new JPanel();
    private JPanel middle = new JPanel();
    private JPanel bottom = new JPanel();
    private JScrollPane tableScroller;
    private JButton apply = new JButton("Apply");
    private JButton settings = new JButton("Settings");
    private JButton logoutExit = new JButton("Logout");
    private JTextField minAmountField = new JTextField(6);
    private JTextField maxAmountField = new JTextField(6);
    private JLabel allTimeIncome = new JLabel("Income this month: 0");
    private JLabel allTimeExpenditure = new JLabel("Expenditure this month: 0");
    private DatePickerSettings toDateSetting = new DatePickerSettings();
    private DatePickerSettings fromDateSetting = new DatePickerSettings();
    private DatePicker fromDate = new DatePicker(fromDateSetting);
    private DatePicker toDate = new DatePicker(toDateSetting);
    private boolean darkTheme = false;
    private JButton toggleThemeButton = new JButton(darkTheme ? "Light theme" : "Dark theme");

    private JTable transactionsTable;
    private LoggedInUser currentUser;

    // GetTransaction variable for getting transaction
    private GetTransaction gt;
    private JPanel mainTable = new JPanel();
    private JLabel empty = new JLabel("No records found :(");
    private DefaultTableModel model;
    String[] types = new String[] {"Any", "Income", "Expenditure"};
    private JComboBox<String> typeCB = new JComboBox<>(types);
    private JButton reset = new JButton("R");
    private LoginUI loginUI;

    public TransactionsUI(JFrame mainFrame, JPanel panel, CardLayout cardLayout, LoggedInUser currentUser, LoginUI loginScreen) {
        parentFrame = mainFrame;
        parentPanel = panel;
        parentLayout = cardLayout;
        this.currentUser = currentUser;
        loginUI = loginScreen;

        // Initialize the UI
        initializeUI();
    }

    public void initializeUI() {
        // Create GetTransaction object with the loggedInUser's id
        gt = new GetTransaction(currentUser.id);

        // Set the JPanel's LayoutManager to BorderLayout
        setLayout(new BorderLayout());
        transactionPanel.setLayout(new BorderLayout());

        // Create get transaction based on the loggedInUser's id and assign it to the 'tr' variable
        Transaction tr = gt.getTransactions();

        // Create a DefaultTableModel to hold the data and column names
        model = new DefaultTableModel(tr.getTableData(), tr.getColumnNames());

        // Announce that the data has been changed
        model.fireTableDataChanged();

        // method to calculate the total income and expenditure
        countTotalIncomeAndExpenditure();

        // Create a new JTable object with the DefaultTableModel reference created above
        transactionsTable = new JTable(model);

        // this method hides the last column since it is about transaction Id and it is irrelevant for the user
        hideLastColumn(transactionsTable);

        // Set editing to false since we have other way to edit records/transactions
        transactionsTable.setEnabled(false);

        // Set the 'top' JPanel's LayoutManager to null and set the size
        top.setLayout(null);
        top.setPreferredSize(new Dimension(1300, 150));

        // Create a label "Transactions" and set the size etc
        JLabel windowLabel = new JLabel("Transactions");
        windowLabel.setBounds(40, 20, 200, 30);
        windowLabel.setFont(new Font("Candara", Font.BOLD, 26));

        JLabel hello = new JLabel("Welcome back: " + currentUser.username);
        hello.setBounds(40, 60, 350, 30);
        hello.setFont(new Font("Candara", Font.PLAIN, 20));

        allTimeIncome.setBounds(400, 60, 450, 30);
        allTimeIncome.setFont(new Font("Candara", Font.PLAIN, 20));
        allTimeExpenditure.setBounds(400, 100, 450, 30);
        allTimeExpenditure.setFont(new Font("Candara", Font.PLAIN, 20));

        JButton addNew = new JButton("+ Add new transaction");
        addNew.setBounds(900, 80, 210, 40);

        // Create a JSeparator component that divides the top panel from the middle panel
        JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        topSeparator.setBounds(20, 140, 1250, 5);

        // Add the components to the top panel
        top.add(hello);
        top.add(windowLabel);
        top.add(allTimeIncome);
        top.add(allTimeExpenditure);
        top.add(addNew);
        top.add(topSeparator);

        // JPanel that consists of the filter components
        JPanel tableHeader = new JPanel();
        tableHeader.setLayout(null);
        tableHeader.setPreferredSize(new Dimension(1300, 45));

        // panel that holds the JTable
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

        // Set the size of the JTable
        transactionsTable.setBounds(0, 0, 1300, 455);

        // Set auto-resize
        transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Set the 5th column or the notes column minimum with since it needs more space than the others
        TableColumn notesColumn = transactionsTable.getColumnModel().getColumn(5);
        notesColumn.setMinWidth(500);

        // Add the table to JScroll pane
        tableScroller = new JScrollPane(transactionsTable);
        tableScroller.setBounds(0,0,1300, 455);

        empty.setBounds(500, 150, 300, 120);
        empty.setFont(new Font("Candara", Font.BOLD, 35));

        // Add the JScrollPane to the JPanel
        mainTable.add(tableScroller);

        // set the middle JPanel's layout manager to border
        middle.setLayout(new BorderLayout());
        middle.setPreferredSize(new Dimension(1300, 500));

        // Add the tableHeader which contains the filters and mainTable which contains the JScrollPane to the middle panel
        middle.add(tableHeader, BorderLayout.NORTH);
        middle.add(mainTable, BorderLayout.CENTER);

        // Add the settings, logout and toggle theme buttons to the bottom panel
        bottom.setPreferredSize(new Dimension(1300, 50));
        bottom.add(settings);
        bottom.add(logoutExit);
        bottom.add(toggleThemeButton);

        // Add the top, middle and bottom conponents to the transactionPanel
        transactionPanel.add(top, BorderLayout.NORTH);
        transactionPanel.add(middle, BorderLayout.CENTER);
        transactionPanel.add(bottom, BorderLayout.SOUTH);

        // add the transactionPanel to the center
        add(transactionPanel, BorderLayout.CENTER);

        // Add listener to the apply button
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // regenerate the table based on the user's specified parameters
                applyCustomFilter();
            }
        });

        // Add listener to the logoutExit button
        logoutExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               performLogout();
            }
        });

        // Checks the table if it has no data in it, more description below
        checkTableData();

        // add table model listener to the DefaultTableModel, this will run whenever the table data is updated
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
                // Shows the add transaction window
                showTransactionAddWindow(parentFrame);
            }
        });

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Shows the settings window
                showSettingsWindow(parentFrame);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Resets the filer inputs and regenerates the table without any parameters
                resetTable(true);
            }
        });

        // add mouse action listener to the JTable
        transactionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle double clicks on the table getClickCount == 2 means double-click
                    if (e.getClickCount() == 2) {
                        // This gets the X and Y parameters of where the click happened
                        Point point = e.getPoint();

                        // Gets which row is clicked based on the point above
                        int clickedRow = transactionsTable.rowAtPoint(point);

                        // Skip the first row since it's about column names
                        if (clickedRow != -1) {
                            // Convert that row's data into array
                            Object[] rowData = new Object[model.getColumnCount()];
                            for (int i = 0; i < rowData.length - 1; i++) {
                                rowData[i] = transactionsTable.getValueAt(clickedRow, i + 1);
                            }

                            // Display the transaction edit window and pass the converted rowData and the parent frame
                            showTransactionEditWindow(parentFrame, rowData);
                        }
                    }

            }
        });


    }

    // This method applies the filters the user selected
    private void applyCustomFilter() {
        double minAmount = 0;
        double maxAmount = 0;

        // Checks and validates the numbers entered into the Min and Max amount fields
        try {
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

        // Create a new GetTransaction object
        gt = new GetTransaction(currentUser.id);

        // Get transactions based on the users input
        Transaction updatedTransactionData = gt.getTransactions(gt.generateQuery(fromDate.getDate(), toDate.getDate(), minAmount, maxAmount, String.valueOf(typeCB.getSelectedItem())));

        // Get the model from the existing table
        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();

        // Remove existing rows
        model.setRowCount(0);

        // Add the rows to the model
        for (Object[] row : updatedTransactionData.getTableData()) {
            model.addRow(row);
        }

        // Announce that the model has been changed
        model.fireTableDataChanged();
    }

    // this checks if a table data is empty or not
    private void checkTableData() {
        if (model.getRowCount() >= 1) {
            // If the row count is greater than or equal to 1, it means at least one row exists
            // so we will display that instead of "No records found"
            Component[] components = mainTable.getComponents();
            for (int i = 0; i < components.length; i++) {
                // loop through the mainTable components and remove the 'empty' component ot the JLabel that says "No records found:
                if (components[i].equals(empty)) {
                    mainTable.remove(i);
                }
            }
            // Re-paint the mainTable after adding the table back
            mainTable.revalidate();
            mainTable.add(tableScroller);
            mainTable.repaint();
        } else {
            // If the row count is 0 remove the tableScroller and instead add the "No records found" Label
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

    // calculates the all-time income and expenditure count
    private void countTotalIncomeAndExpenditure() {
        double exp = 0;
        double inc = 0;
        String type = "";

        // Get the row count of the table
        int rows = model.getRowCount();

        // loop through all rows
        for (int i = 0; i < rows; i++) {
            // Check the type of that specific row whether it is "Income" or "Expenditure"
            type = String.valueOf(model.getValueAt(i, 3));
            if (type.equals("Income")) {
                // If it is income, get the amount which is found at column 1 and increment it to the 'inc' variable
                inc = inc + Double.parseDouble(model.getValueAt(i, 1).toString());
            } else {
                // If it is expenditure, get the amount which is found at column 1 and increment it to the 'exp' variable
                exp = exp + Double.parseDouble(model.getValueAt(i, 1).toString());
            }

        }
        // Set the text of the allTimeIncome and allTimeExpenditure to the respective amounts
        allTimeIncome.setText("Total income: " + inc);
        allTimeExpenditure.setText("Total expenditure: " + exp);
    }

    // this method switches the theme to dark or light
    private void toggleTheme() {
        try {
            // If the current theme is Dark
            if (darkTheme) {
                // set the button text to "Dark theme" since the theme is going to be changed to light,
                // we want to show the option to switch to dark mode
                toggleThemeButton.setText("Dark theme");

                // Set the look and feel to light theme form the FlatLaf library
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                // Do the same thing but for light theme
                toggleThemeButton.setText("Light theme");
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }
            // update the UI
            FlatLaf.updateUI();

            // reverse the boolean value
            darkTheme = !darkTheme;
        } catch (UnsupportedLookAndFeelException e) {
            // Catch any errors
            e.printStackTrace();
        }
    }


    // this method shows the transaction add window
    private void showTransactionAddWindow(JFrame parentFrame) {
        // Set-up components
        JDialog dialog = new JDialog(parentFrame, "Add transaction", false);
        dialog.setSize(500, 500);
        JPanel transactionInputPanel = new JPanel();
        JTextField amount = new JTextField(10);
        JLabel amountText = new JLabel("Amount:");
        JComboBox<String> typeCB = new JComboBox<>(new String[]{"Income", "Expenditure"});
        typeCB.setSelectedIndex(1);
        JLabel typeText = new JLabel("Type:");
        JLabel categoryText = new JLabel("Category:");
        DatePickerSettings transactionDateSetting = new DatePickerSettings();
        DatePicker transactionDate = new DatePicker(transactionDateSetting);
        JLabel dateText = new JLabel("Date:");
        JButton addButton = new JButton("+ ADD");
        JLabel notesText = new JLabel("Notes:");
        JTextArea notes = new JTextArea(5, 15);
        JComboBox<String> categoryCB = new JComboBox<>(new String[]{"-", "Bill", "Utilities", "Rent", "Mortgage", "Groceries", "Transportation", "Healthcare", "Entertainment", "Education", "Debt Repayment", "Personal care", "Miscellaneous", "Food"});

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

        typeCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (typeCB.getSelectedItem().toString().compareTo("Income") == 0) {
                    categoryCB.setSelectedIndex(0);
                    categoryCB.setEnabled(false);
                    countTotalIncomeAndExpenditure();
                } else {
                    categoryCB.setEnabled(true);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // the addTransactionIntoDB method returns true if it succeeded and false if it failed
                if (addTransactionIntoDB(amount.getText(), String.valueOf(typeCB.getSelectedItem()), categoryCB.getSelectedIndex() + 1, transactionDate.getDate(), notes.getText())) {
                    // Display transaction added successfully message and close the dialog then reset the table to fetch the latest entry
                    Components.displayOptionPane("Transaction added successfully!", 1);
                    dialog.setVisible(false);
                    resetTable(false);
                    countTotalIncomeAndExpenditure();
                } else {
                    // Show that it failed
                    Components.displayOptionPane("Transaction added failed!", 0);
                }

            }
        });
    }

    // this methods displays the transaction edit window, it takes the parentFrame and the rowData as inputs
    private void showTransactionEditWindow(JFrame parentFrame, Object[] rowData) {
        // Set-up components
        JDialog dialog = new JDialog(parentFrame, "Edit transaction", false);
        dialog.setSize(500, 500);
        JPanel transactionInputPanel = new JPanel();

        // set the amount to the existing amount from the row
        JTextField amount = new JTextField(rowData[0].toString(), 10);
        JLabel amountText = new JLabel("Amount:");
        JComboBox<String> typeCB = new JComboBox<>(new String[]{"Income", "Expenditure"});
        // set the type to the existing type from the rows
        typeCB.setSelectedItem(rowData[2]);
        JLabel typeText = new JLabel("Type:");
        JLabel categoryText = new JLabel("Category:");
        DatePickerSettings transactionDateSetting = new DatePickerSettings();
        DatePicker transactionDate = new DatePicker(transactionDateSetting);
        JLabel dateText = new JLabel("Date:");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JLabel notesText = new JLabel("Notes:");
        JTextArea notes;
        JComboBox<String> categoryCB = new JComboBox<>(new String[]{"-", "Bill", "Utilities", "Rent", "Mortgage", "Groceries", "Transportation", "Healthcare", "Entertainment", "Education", "Debt Repayment", "Personal care", "Miscellaneous", "Food"});
        categoryCB.setSelectedItem(rowData[3]);

        // if the notes that comes from the row is not empty, set the default text of the text area to the notes that comes from the rows array
        if (rowData[4] != null) {
           notes = new JTextArea(rowData[4].toString(), 5, 15);
        } else {
            notes = new JTextArea(5, 15);
        }

        transactionInputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        transactionInputPanel.add(new JLabel("Fill in the inputs to update transaction:"), gbc);

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
        transactionInputPanel.add(updateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        transactionInputPanel.add(deleteButton, gbc);

        dialog.add(transactionInputPanel);
        Components.centerDialogOnScreen(dialog);
        dialog.setResizable(false);
        dialog.setVisible(true);

        typeCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (typeCB.getSelectedItem().toString().compareTo("Income") == 0) {
                    categoryCB.setSelectedIndex(0);
                    categoryCB.setEnabled(false);
                } else {
                    categoryCB.setEnabled(true);
                    categoryCB.setSelectedItem(rowData[3]);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (performTransactionDelete(Integer.parseInt(rowData[5].toString()))) {
                    Components.displayOptionPane("Transaction deleted successfully!", 1);
                    dialog.setVisible(false);
                    resetTable(false);
                    countTotalIncomeAndExpenditure();
                } else {
                    Components.displayOptionPane("Transaction deletion failed!", 0);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // the updateTransactionInDB returns true if it succeeded or false if it failed
                if (updateTransactionInDB(amount.getText(), String.valueOf(typeCB.getSelectedItem()), categoryCB.getSelectedIndex() + 1, transactionDate.getDate(), notes.getText(), Integer.parseInt(rowData[5].toString()), rowData)) {
                    // show success message, hide the dialog and refresh the table
                    Components.displayOptionPane("Transaction updated successfully!", 1);
                    dialog.setVisible(false);
                    resetTable(false);
                    countTotalIncomeAndExpenditure();
                } else {
                    Components.displayOptionPane("Transaction update failed!", 0);
                }

            }
        });
    }

    // this method takes the user inputs and tries to insert it into the database and returns true if it succeeded and false if it hasn't
    boolean addTransactionIntoDB(String amount, String type, int category, LocalDate date, String notes) {
        double trAmount = 0;
        // validate amount
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

        // Create a InsertTransaction object
        InsertTransaction addTr = new InsertTransaction();

        // invoke the addTransaction method and pass the validated user input, it returns true if the input succeeded and false if not
        return addTr.addTransaction(addTr.generateQuery(trAmount, type, category, date, notes, currentUser.id));
    }


    // this method updates a specific transaction into the database, it takes the updated data + the original data and returns true if it successfully updated and false if not
    boolean updateTransactionInDB(String amount, String type, int category, LocalDate date, String notes, int tId, Object[] original) {
        double trAmount = 0;

        // validate amount
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

        // if the user didn't specify a new date use the existing one
        String newDate = "";
        if (date == null) {
            newDate = original[1].toString();
        } else {
            newDate = date.toString();
        }

        // create a new update transaction object based on the validated user input
        UpdateTransaction updateTr = new UpdateTransaction(tId, trAmount, newDate, type, category, notes);

        // invoke the updateTransaction method, this updates the record and returns true if it succeeded or false if it failed
        return updateTr.updateTransaction();
    }

    boolean performTransactionDelete(int txnId) {
        return UpdateTransaction.deleteTransaction(txnId);
    }

    // this method resets the table by fetching data without any parameters,
    // it takes a resetFields argument that specifies whether the user entered filters should be set to empty or ot
    private void resetTable(boolean resetFields) {
        if (resetFields) {
            minAmountField.setText("");
            maxAmountField.setText("");
            fromDate.setText("");
            toDate.setText("");
            typeCB.setSelectedIndex(0);
        }

        // create a new transaction object by getting fresh transaction without any parameters
        Transaction updatedTransactionData = gt.getTransactions();

        // get the table model
        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();

        // set the row count to 0
        model.setRowCount(0);

        // loop through all rows and add it into the model
        for (Object[] row : updatedTransactionData.getTableData()) {
            model.addRow(row);
        }

        // announce that the model has been updated
        model.fireTableDataChanged();
    }

    // method to show the user edit window
    private void showSettingsWindow(JFrame parentFrame) {
        // setup components with the current user info
        JDialog dialog = new JDialog(parentFrame, "Edit user", false);
        dialog.setSize(500, 500);
        JPanel userInputPanel = new JPanel();
        JTextField firstNameField = new JTextField(currentUser.firstName, 10);
        JLabel firstNameText = new JLabel("First name:");
        JTextField lastNameField = new JTextField(currentUser.lastName, 10);
        JLabel lastNameText = new JLabel("Last name:");
        JTextField usernameField = new JTextField(currentUser.username, 10);
        JLabel usernameText = new JLabel("Username:");
        JTextField emailField = new JTextField(currentUser.email, 10);
        JLabel emailText = new JLabel("Email:");
        JPasswordField newPassword = new JPasswordField(currentUser.password, 10);
        JLabel newPasswordText = new JLabel("New password:");
        JPasswordField newPasswordConfirm = new JPasswordField(currentUser.password, 10);
        JLabel newPasswordConfirmText = new JLabel("Repeat password:");

        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        userInputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        userInputPanel.add(new JLabel("Update your info here:"), gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(firstNameText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        userInputPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(lastNameText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        userInputPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(usernameText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        userInputPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(emailText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        userInputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(newPasswordText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        userInputPanel.add(newPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        userInputPanel.add(newPasswordConfirmText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        userInputPanel.add(newPasswordConfirm, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        userInputPanel.add(updateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        userInputPanel.add(deleteButton, gbc);

        dialog.add(userInputPanel);
        Components.centerDialogOnScreen(dialog);
        dialog.setResizable(false);
        dialog.setVisible(true);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (performUserDelete(currentUser.id)) {
                    Components.displayOptionPane("You deleted your account successfully, adios!", 1);
                    dialog.setVisible(false);
                    performLogout();
                } else {
                    Components.displayOptionPane("Account deletion failed, you're stuck with us :)", 0);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // the performUserUpdate method takes the updated user info and tries to update it in the database,
                // it returns true if it succeeded and false if it failed
                if (performUserUpdate(usernameField.getText(), String.valueOf(newPassword.getPassword()), String.valueOf(newPasswordConfirm.getPassword()), firstNameField.getText(), lastNameField.getText(), emailField.getText())) {
                    // show success message
                    Components.displayOptionPane("User info updated successfully", 1);
                    // hide the dialog
                    dialog.setVisible(false);
                } else {
                    // show that it has failed
                    Components.displayOptionPane("User info update failed", 0);
                }
            }
        });
    }

    // this method receives user input and updates the user info in the database
    boolean performUserUpdate(String username, String password, String passwordRepeat, String firstName, String lastName, String email) {
        // perform validations like the signup
        if (username.length() < 5) {
            Components.displayOptionPane("Username should be at least 5 characters long", 0);
            return false;
        }
        if (password.length() < 8) {
            Components.displayOptionPane("Password should be at least 8 characters long", 0);
            return false;
        }
        if (passwordRepeat.compareTo(password) != 0) {
            Components.displayOptionPane("The passwords entered don't match", 0);
            return false;
        }

        if (firstName.length() < 2 || lastName.length() < 2) {
            Components.displayOptionPane("Name should be at least 2 characters long", 0);
            return false;
        }

        if (!email.contains("@")) {
            Components.displayOptionPane("Enter a valid email address", 0);
            return false;
        }

        // create a SignupUser object with the new user info
        SignupUser signup = new SignupUser(firstName, lastName, username.toLowerCase(), email, password, currentUser.id);

        // check if the username and email exists if only the current user info's email and username and the new email and username differs
        if (currentUser.username.compareTo(username) != 0 && currentUser.email.compareTo(email) != 0 && signup.checkUsernameAndEmail()) {
            return false;
        }

        // the updateUser methods update the user info in the db and returns true if it succeeded and false if it failed
        return signup.updateUser();
    }

    // this method deletes a user form the database
    boolean performUserDelete(int id) {
        return SignupUser.deleteUser(id);
    }

    // this method takes a JTable and hides the last column which is about transaction id and it is unnecessary to the user
    private static void hideLastColumn(JTable table) {
        // get the table model from the JTable
        TableColumnModel columnModel = table.getColumnModel();

        // get the last column
        int lastIndex = columnModel.getColumnCount() - 1;

        // Set the last column width to 0
        columnModel.getColumn(lastIndex).setMinWidth(0);
        columnModel.getColumn(lastIndex).setMaxWidth(0);
        columnModel.getColumn(lastIndex).setWidth(0);
    }

    void performLogout() {
        // loop through the parentPanel's components and remove "transactionsScreen" since
        // if we won't, it might cause a stackoverflow error
        for (Component c : parentPanel.getComponents()) {
            if (c.getName() != null && c.getName().compareTo("transactionsScreen") == 0) {
                parentPanel.remove(c);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        }

        // Then show the loginScreen
        parentLayout.show(parentPanel, "loginScreen");

        // Call the setupUI to set the screen size and title again
        loginUI.setupUI();
    }
}
