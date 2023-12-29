package db_controller;
import model.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUserInfo {
        ArrayList<UserInfo> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        DBInstance dbI = new DBInstance();

        public ArrayList<UserInfo> getUsers() {

            try  {
                Connection conn = dbI.connectDB();
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    users.add(new UserInfo( resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("created_at"), resultSet.getInt("id")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
    }
}
