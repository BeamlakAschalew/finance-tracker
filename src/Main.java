import db_controller.DBInstance;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DBInstance db = new DBInstance();
        Connection conn;
        try {
            conn = db.connectDB();
            System.out.println(conn.getMetaData());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}