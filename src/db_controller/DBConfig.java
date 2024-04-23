package db_controller;

// this is the database configuration that consists of database name, username, password etc
public class DBConfig {
    final static String url = "localhost"; // Assuming your Oracle database is running locally
    final static int port = 1521; // Default Oracle port
    final static String service = "orcl"; // Replace "XE" with your Oracle service name if different

    public final static String path = "jdbc:oracle:thin:@" + url + ":" + port + ":" + service;
    public final static String username = "expense_tracker_v2";
    public final static String password = "expense_tracker";
}
