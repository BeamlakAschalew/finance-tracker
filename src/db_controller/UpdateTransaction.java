package db_controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTransaction {
    private int txnId;
    private double amount;
    private String date;
    private String type;
    private int categoryId;
    private String notes;


    public UpdateTransaction(int tid, double am, String dt, String ty, int cId, String nt) {
        txnId = tid;
        amount = am;
        date = dt;
        type = ty;
        categoryId = cId;
        notes = nt;
    }

    // this method updates an existing transaction
    public boolean updateTransaction() {
        String query = "UPDATE transactions SET amount = ?, txn_date = TO_DATE(?, 'YYYY-MM-DD'), type = ?, category_id = ?, notes = ? WHERE txn_id = ?";
        try {
            Connection conn = DBInstance.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);

            // replace the placeholders with the actual user inputs
            statement.setDouble(1, amount);
            statement.setString(2, date);
            statement.setString(3, type);
            statement.setInt(4, categoryId);
            statement.setString(5, notes);
            statement.setInt(6, txnId);

            // execute and assign the affected rows count to a variables
            int affectedRowsCount = statement.executeUpdate();

            if (affectedRowsCount == 1) {
                // return true if the affected rows is 1 which is successful update
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false if an error occurs
            return false;
        }

        // code should not reach here but if it does return false
        return false;
    }

    // this method deletes a transaction from the database
    static public boolean deleteTransaction(int txnId) {
        String query = "DELETE FROM transactions WHERE txn_id = ?";
        try {
            Connection conn = DBInstance.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);

            // replace the placeholders with the actual user inputs
            statement.setInt(1, txnId);

            // execute and assign the affected rows count to a variables
            int affectedRowsCount = statement.executeUpdate();

            if (affectedRowsCount == 1) {
                // return true if the affected rows is 1 which is successful delete
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false if an error occurs
            return false;
        }

        // code should not reach here but if it does return false
        return false;
    }
}
