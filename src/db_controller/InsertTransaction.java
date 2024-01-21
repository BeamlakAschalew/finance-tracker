package db_controller;

import components.Components;
import model.InputQuery;

import java.sql.*;
import java.time.LocalDate;

public class InsertTransaction {
    private String username;
    public InsertTransaction(String username) {
        this.username = username;
    }


    public boolean addTransaction(InputQuery iq) {
        String masterQuery = iq.query + " VALUES (" + iq.accId + ", " + iq.amount + ", '" + iq.type + "'" + ", " + iq.catId;
        if (iq.dt) {
            masterQuery += ", '" + iq.date + "'";
        }
        if (iq.note) {
            masterQuery += ", '" + iq.notes + "'";
        }
        masterQuery += ");";

        DBInstance dbInstance = new DBInstance();
        try {
            Connection conn = dbInstance.connectDB();
            PreparedStatement preparedStatement = conn.prepareStatement(masterQuery);

            int results = preparedStatement.executeUpdate();
            System.out.println("Operation: " + results);
            return results == 1;
        } catch (SQLException e) {
            Components.displayOptionPane("Insert error occurred", 0);
            return false;
        } catch (Exception e) {
            Components.displayOptionPane("Error occurred", 0);
            return false;
        }
    }

    public InputQuery generateQuery(double amount, String type, int category, LocalDate date, String notes, int id) {
        boolean isDateProvided = date != null;
        boolean isNoteProvided = notes != null && !notes.isEmpty();

        String query = "INSERT INTO transactions (user_id, amount, type, category_id";

        if (isDateProvided) {
            query += ", date";
        }
        if (isNoteProvided) {
            query += ", notes";
        }

        query += ")";
        System.out.println("QUERY: " + query);
        return new InputQuery(isDateProvided, isNoteProvided, query, notes, type, id, category, date, amount);
    }
}
