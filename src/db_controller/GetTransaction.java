package db_controller;

import model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GetTransaction {

    private int id;

    // column names for the table
    private String[] columnNames = new String[]{"No.", "Amount", "Date", "Type", "Category", "Notes", "TXN Id"};
    public GetTransaction(int id) {
        this.id = id;
    }


    // this method gets transaction based on user id without any parameters
    public Transaction getTransactions() {
        // SQL query to fetch the transactions, uses inner-join operation between users, transactions and transaction_categories tables
        String query = "SELECT amount, txn_date, type, category_name, notes, txn_id FROM transactions JOIN users ON users.id = transactions.user_id JOIN transaction_categories ON transactions.category_id = transaction_categories.category_id WHERE users.id = ?";

        // create a 2D array that holds the values that come from the select operation
        Object[][] dataArray = new Object[0][];

        try {
            // Create a connection between the database
            Connection conn = DBInstance.connectDB();

            // prepare a statement
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            // set the '?' value to be the user id
            preparedStatement.setInt(1, id);

            // execute the query and assign it to a results variable
            ResultSet results = preparedStatement.executeQuery();

            // get the metadata of the ResultSet from the results variable
            ResultSetMetaData metaData = results.getMetaData();

            // get the column count from the metadata
            int columnCount = metaData.getColumnCount();

            // Fetch data and convert it to a List of Object arrays
            ArrayList<Object[]> dataList = new ArrayList<>();

            // this variable gets hold of the number of rows inorder to be displayed in the first column
            int rowCount = 0;

            while (results.next()) {
                // increment the rowCount everytime the while loop runs
                rowCount++;

                // create a new array everytime that holds values of that specific row,
                // size is columnCount + 1 because there is a rowCount that doesn't exists in the database and we have to include that
                Object[] rowData = new Object[columnCount + 1];

                // set the rowData at 0 to always be the rowCount
                rowData[0] = rowCount;

                // loop through the columns
                for (int i = 1; i <= columnCount; i++) {
                    // assign the values to the rowData
                    rowData[i] = results.getObject(i);
                }
                // add that specific rowData to the Object array
                dataList.add(rowData);
            }

            // Convert the List to a 2D array
            dataArray = new Object[dataList.size()][];
            dataList.toArray(dataArray);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // return the Transaction object which contains columnNames and the data array
        return new Transaction(columnNames, dataArray);
    }

    // this method is used to fetch transactions based on a custom SQL query,
    // the operation is the same as the above one but this method takes a query which is a String
    public Transaction getTransactions(String query) {
        Object[][] dataArray = new Object[0][];
        try {
            Connection conn = DBInstance.connectDB();
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

    // this method generates a custom SQL query for fetching transactions based on the user's input
    public String generateQuery(LocalDate startDate, LocalDate endDate, double minAmount, double maxAmount, String type) {
        // create the base, minimal query
        String query = "SELECT amount, txn_date, type, category_name, notes, txn_id FROM transactions JOIN users ON users.id = transactions.user_id JOIN transaction_categories ON transactions.category_id = transaction_categories.category_id WHERE users.id = '" + id + "'";

        // if startDate is not empty, add that as a parameter
        if (startDate != null) {
            if (!startDate.toString().isEmpty()) {
                query += "AND txn_date >= TO_DATE('" + startDate + "', 'YYYY-MM-DD')";
            }
        }

        // if endDate is not empty, add that as a parameter
        if (endDate != null) {
            if (!endDate.toString().isEmpty()) {
                query += " AND txn_date <= TO_DATE('" + endDate + "', 'YYYY-MM-DD')";
            }
        }

        // if minimum amount is not 0, add that as a parameter
        if (minAmount != 0) {
                query += " AND amount >= " + minAmount;
        }

        // if maximum amount is not 0, add that as a parameter
        if (maxAmount != 0) {
            query += " AND amount =< " + maxAmount;
        }

        // if the type is not "Any" add "Income" or "Expenditure" as values for the "type"
        if (type.compareTo("Any") != 0) {
            if (type.compareTo("Income") == 0) {
                query += " AND type = " + "\"Income\"";
            } else {
                query += " AND type = " + "\"Expenditure\"";
            }
        }

        // close the query
        // query += ";";

        // return the query string
        return query;
    }
}
