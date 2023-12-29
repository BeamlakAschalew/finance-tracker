// LET'S DO THISSS

import db_controller.GetTransaction;
import db_controller.GetUserInfo;
import model.Transaction;
import model.UserInfo;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {
    Main() {
        GetUserInfo gui = new GetUserInfo();
        GetTransaction gt = new GetTransaction();

        ArrayList<UserInfo> users = gui.getUsers();
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