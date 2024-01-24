package db_controller;

// this is the database configuration that consists of database name, username, password etc
public class DBConfig {
    final static String dbName = "finance_test";
    final static String url = "localhost";
    public final static String path = "jdbc:mysql://" + url + "/" + dbName;
    public final static String username = "root";

    public final static String password = "new_password";


}
