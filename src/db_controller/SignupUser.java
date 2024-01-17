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

    public SignupUser(String fn, String ln, String un, String em, String pa) {
        firstName = fn;
        lastName = ln;
        username = un;
        email = em;
        password = pa;
    }

    String query = "INSERT INTO users (first_name, last_name, username, email, password) VALUES (?, ?, ?, ?, MD5(?))";

    DBInstance dbI = new DBInstance();

    public SignupResponse registerUser() {
        try {
            Connection conn = dbI.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, email);
            statement.setString(5, password);

            int affectedRowsCount = statement.executeUpdate();

            if (affectedRowsCount == 1) {
                return new SignupResponse(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SignupResponse(3);
        }

        return new SignupResponse(3);
    }

    public boolean checkUsernameAndEmail() {
        try {
            String query = "SELECT username, email FROM users";
            Connection conn = dbI.connectDB();
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet data = statement.executeQuery();

            while (data.next()) {
                if (data.getString("username").compareTo(username) == 0 || data.getString("email").compareTo(email) == 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


}
