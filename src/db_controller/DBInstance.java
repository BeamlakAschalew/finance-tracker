package db_controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInstance {

    // this static method returns a connection to the database based on the database configuration in the DBConfig.java
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DBConfig.path, DBConfig.username, DBConfig.password);
            System.out.println("Success");
            return connection;
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
