package db_controller;

import model.SignupResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignupUser {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;
    private int id;

    public SignupUser(String fn, String ln, String un, String em, String pa) {
        firstName = fn;
        lastName = ln;
        username = un;
        email = em;
        password = pa;
    }

    public SignupUser(String fn, String ln, String un, String em, String pa, int id) {
        firstName = fn;
        lastName = ln;
        username = un;
        email = em;
        password = pa;
        this.id = id;
    }

    // this method registers a user and returns a SignupResponse object
    public SignupResponse registerUser() {
        String query = "INSERT INTO users (first_name, last_name, username, email, password) VALUES (?, ?, ?, ?, (SELECT standard_hash(?, 'MD5') FROM dual))";
        try {
            Connection conn = DBInstance.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);

            // replace the placeholders with the actual user input
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, email);
            statement.setString(5, password);

            // execute the create user operation
            int affectedRowsCount = statement.executeUpdate();

            // check if the affected rows are 1 and return a SignupResponse with error code 0 which is successful
            if (affectedRowsCount == 1) {
                return new SignupResponse(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return a SignupResponse with error code 3 which is unknown error
            return new SignupResponse(3);
        }

        // the code shouldn't reach this stage but if it does it's probably an error so return error code 3
        // return a SignupResponse with error code 3
        return new SignupResponse(3);
    }

    // this method updates a user and returns true if it succeeded and false if it failed
    public boolean updateUser() {
        String query = "UPDATE users SET first_name = ?, last_name = ?, username = ?, email = ?, password = (SELECT standard_hash(?, 'MD5') FROM dual) WHERE id = ?";
        try {
            Connection conn = DBInstance.connectDB();

            // prepare a statement
            PreparedStatement statement = conn.prepareStatement(query);

            // set the values of the placeholders with the actual user input
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setInt(6, id);

            // execute the update
            int affectedRowsCount = statement.executeUpdate();

            // return true if one row is affected
            if (affectedRowsCount == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false if the code fails
            return false;
        }

        // the code should not reach here since a value must be returned in the above blocks but we return false if it does
        return false;
    }

    // this method deletes a user and returns true if it succeeded and false if it failed
    static public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try {
            Connection conn = DBInstance.connectDB();
            // prepare a statement
            PreparedStatement statement = conn.prepareStatement(query);

            // set the values of the placeholders with the actual user input
            statement.setInt(1, id);

            // execute the update
            int affectedRowsCount = statement.executeUpdate();

            // return true if one row is affected
            if (affectedRowsCount == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false if the code fails
            return false;
        }

        // the code should not reach here since a value must be returned in the above blocks but we return false if it does
        return false;
    }

    // this method checks if username and email exists
    public boolean checkUsernameAndEmail() {
        try {
            String query = "SELECT username, email FROM users";
            Connection conn = DBInstance.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet data = statement.executeQuery();

            while (data.next()) {
                // checks if the user input's username and email is equal with the one that comes from the database and return true if it does
                if (data.getString("username").compareTo(username) == 0 || data.getString("email").compareTo(email) == 0) {
                    return true;
                }
            }

            // return false since the user selected username and password doesn't exists in the database
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // if a problem occurs return true
            return true;
        }
    }
}
