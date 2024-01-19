package db_controller;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetTransaction {

    String username;
    public GetTransaction(String username) {
        this.username = username;
    }
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBInstance dbInstance = new DBInstance();
    String query = "SELECT amount AS Amount, date AS Date, type AS Type, category_name AS Category, notes AS Notes FROM transactions JOIN accounts ON transactions.account_id = accounts.account_id JOIN users ON users.id = accounts.user_id JOIN transaction_category ON transactions.category_id = transaction_category.category_id WHERE users.username = ?;";

    /**
     *  Gets list of transactions from the database
     * */
    public Transaction getTransactions() {
        Object[][] dataArray = new Object[0][];
        String[] columnNames = new String[0];
        try {
            Connection conn = dbInstance.connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet results = preparedStatement.executeQuery();
            ResultSetMetaData metaData = results.getMetaData();

            int columnCount = metaData.getColumnCount();
            columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Fetch data and convert it to a List of Object arrays
            ArrayList<Object[]> dataList = new ArrayList<>();
            while (results.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = results.getObject(i);
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
}
