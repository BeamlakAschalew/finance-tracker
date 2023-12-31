package db_controller;
import model.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUserInfo {

        String username, password;

        public GetUserInfo (String username, String password) {
            this.username = username;
            this.password = password;
        }
        ArrayList<UserInfo> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE username = ? AND password = MD5(?)";

        DBInstance dbI = new DBInstance();

        public ArrayList<UserInfo> getUser() {

            try  {
                Connection conn = dbI.connectDB();
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("first_name"));
                    users.add(new UserInfo( resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("created_at"), resultSet.getInt("id")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
    }
}
