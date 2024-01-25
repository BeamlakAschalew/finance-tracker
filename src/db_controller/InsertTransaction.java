package db_controller;

import components.Components;
import model.InputQuery;

import java.sql.*;
import java.time.LocalDate;

public class InsertTransaction {

    // this method takes an InputQuery and adds a transaction into the database and returns true if it completed successfully and false if it failed
    public boolean addTransaction(InputQuery iq) {
        // generate master query by concatenating the base query that comes from the InputQuery object
        // add account id, amount, type, category id values in the query since those are mandatory
        String masterQuery = iq.query + " VALUES (" + iq.accId + ", " + iq.amount + ", '" + iq.type + "'" + ", " + iq.catId;

        // if date is provided add a date parameter to the values ()
        if (iq.dateProvided) {
            masterQuery += ", '" + iq.date + "'";
        }

        // if note is provided add a notes parameter to the values ()
        if (iq.noteProvided) {
            masterQuery += ", '" + iq.notes + "'";
        }

        // close the master query
        masterQuery += ");";
        System.out.println(masterQuery);
        try {
            Connection conn = DBInstance.connectDB();

            // execute the masterQuery
            PreparedStatement preparedStatement = conn.prepareStatement(masterQuery);

            int results = preparedStatement.executeUpdate();

            // return true if the affected rows are 1
            return results == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Components.displayOptionPane("Insert error occurred", 0);

            // return false if the operation failed
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Components.displayOptionPane("Error occurred", 0);

            // return false if the operation failed
            return false;
        }
    }

    // this method generates custom insert query based on user input
    public InputQuery generateQuery(double amount, String type, int category, LocalDate date, String notes, int id) {
        boolean isDateProvided = date != null;
        boolean isNoteProvided = notes != null && !notes.isEmpty();

        // base query since user_id, amount, type and category_id are mandatory
        String query = "INSERT INTO transactions (user_id, amount, type, category_id";

        // add a date field if a date is provided
        if (isDateProvided) {
            query += ", date";
        }

        // add a notes field if a note is provided
        if (isNoteProvided) {
            query += ", notes";
        }

        // close the query
        query += ")";

        // return the InputQuery object
        return new InputQuery(isDateProvided, isNoteProvided, query, notes, type, id, category, date, amount);
    }
}
