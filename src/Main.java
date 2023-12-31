// LET'S DO THISSS

import db_controller.GetTransaction;
import db_controller.GetUserInfo;
import model.Transaction;
import model.UserInfo;
import ui.ScreenManager;
import java.util.ArrayList;


public class Main {
    Main() {
        new ScreenManager().display();
        GetUserInfo gui = new GetUserInfo("beamlak", "password");
        GetTransaction gt = new GetTransaction();

        ArrayList<UserInfo> users = gui.getUser();
        ArrayList<Transaction> transactions = gt.getTransactions();
        for (UserInfo u : users) {
            System.out.println("First name: " + u.fname);
            System.out.println("Last name: " + u.lname);
            System.out.println("username: " + u.username);
            System.out.println("-------------------------------------------");
        }

        for (Transaction t : transactions) {
            System.out.println("Amount: " + t.amount);
            System.out.println("Date: " + t.date);
            System.out.println("-------------------------------------------");
        }


    }
    public static void main(String[] args) {
        new Main();
    }

}