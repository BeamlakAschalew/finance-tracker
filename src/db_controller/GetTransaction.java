package db_controller;

import model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetTransaction {

    String username;
    String[] columnNames = new String[]{"No.", "Amount", "Date", "Type", "Category", "Notes"};
    public GetTransaction(String username) {
        this.username = username;
    }
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBInstance dbInstance = new DBInstance();


    /**
     *  Gets list of transactions from the database
     * */
    public Transaction getTransactions() {
        String query = "SELECT amount AS Amount, date AS Date, type AS Type, category_name AS Category, notes AS Notes FROM transactions JOIN users ON users.id = transactions.user_id JOIN transaction_categories ON transactions.category_id = transaction_categories.category_id WHERE users.username = ?;";
        Object[][] dataArray = new Object[0][];
        try {
            Connection conn = dbInstance.connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet results = preparedStatement.executeQuery();
            ResultSetMetaData metaData = results.getMetaData();

            int columnCount = metaData.getColumnCount();

            // Fetch data and convert it to a List of Object arrays
            ArrayList<Object[]> dataList = new ArrayList<>();
            int rowCount = 0;
            while (results.next()) {
                rowCount++;
                Object[] rowData = new Object[columnCount + 1];
                rowData[0] = rowCount;
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i] = results.getObject(i);
                }
                dataList.add(rowData);
            }

            // Convert the List to a 2D array
            dataArray = new Object[dataList.size()][];
            dataList.toArray(dataArray);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Transaction(columnNames, dataArray);
    }

    public Transaction getTransactions(String query) {
        Object[][] dataArray = new Object[0][];
        try {
            Connection conn = dbInstance.connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet results = preparedStatement.executeQuery();
            ResultSetMetaData metaData = results.getMetaData();

            int columnCount = metaData.getColumnCount();

            // Fetch data and convert it to a List of Object arrays
            ArrayList<Object[]> dataList = new ArrayList<>();
            int rowCount = 0;
            while (results.next()) {
                rowCount++;
                Object[] rowData = new Object[columnCount + 1];
                rowData[0] = rowCount;
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i] = results.getObject(i);
                }
                dataList.add(rowData);
            }

            // Convert the List to a 2D array
            dataArray = new Object[dataList.size()][];
            dataList.toArray(dataArray);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Transaction(columnNames, dataArray);
    }
    public String generateQuery(LocalDate startDate, LocalDate endDate, double minAmount, double maxAmount, String type) {
        String query = "SELECT amount AS Amount, date AS Date, type AS Type, category_name AS Category, notes AS Notes FROM transactions JOIN users ON users.id = transactions.user_id JOIN transaction_categories ON transactions.category_id = transaction_categories.category_id WHERE users.username = '" + username + "'";

        if (startDate != null) {
            if (!startDate.toString().isEmpty()) {
                query += "AND Date(date) >= Date('" + startDate + "')";
            }
        }
        if (endDate != null) {
            if (!endDate.toString().isEmpty()) {
                query += " AND Date(date) <= Date('" + endDate + "')";
            }
        }
        if (minAmount != 0) {
                query += " AND amount > " + minAmount;
        }

        if (maxAmount != 0) {
            query += " AND amount < " + maxAmount;
        }

        if (type.compareTo("Any") != 0) {
            if (type.compareTo("Income") == 0) {
                query += " AND type = " + "\"Income\"";
            } else {
                query += " AND type = " + "\"Expenditure\"";
            }
        }
        query += ";";
        System.out.println("QUERY: " + query);
        return query;
    }



}
