package model;

import com.mysql.cj.log.Log;

public class LoggedInUser {
    String username;
    String password;
    boolean isLoggedIn;

    LoggedInUser() {

    }

    LoggedInUser(String u, String p, boolean l) {
        username = u;
        password = p;
        isLoggedIn = l;
    }

}
