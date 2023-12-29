package db_controller;

import model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetTransaction {
    ArrayList<Transaction> transactions = new ArrayList<>();
    DBInstance dbInstance = new DBInstance();
    String query = "SELECT * FROM transactions";

    /**
     *  Gets list of transactions from the database
     * */
    public ArrayList<Transaction> getTransactions() {
        try {
            Connection conn = dbInstance.connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                transactions.add(new Transaction(results.getInt("txn_id"), results.getInt("account_id"), results.getInt("category_id"), results.getDouble("amount"), results.getString("notes"), results.getString("type"), results.getString("date")));
            }
        } catch (SQLException ex) {


            ex.printStackTrace();
        }

        return transactions;

    }
}
