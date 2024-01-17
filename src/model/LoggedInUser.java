package model;

public class LoggedInUser {
    public String firstName, lastName, email, username;


    boolean isLoggedIn;

    public LoggedInUser(String un, String fn, String ln, String em, boolean loggedIn) {
        username = un;
        isLoggedIn = loggedIn;
        firstName = fn;
        lastName = ln;
        email = em;
    }

    public LoggedInUser(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
