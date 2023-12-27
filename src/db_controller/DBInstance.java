package db_controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInstance {

    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DBConfig.path, DBConfig.username, DBConfig.password);
            System.out.println("Success");
            return connection;
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
