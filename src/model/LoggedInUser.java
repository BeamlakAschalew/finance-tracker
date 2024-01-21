package model;

public class LoggedInUser {
    public String firstName, lastName, email, username;
    public int id;


    boolean isLoggedIn;

    public LoggedInUser(String un, String fn, String ln, String em, boolean loggedIn, int id) {
        username = un;
        isLoggedIn = loggedIn;
        firstName = fn;
        lastName = ln;
        email = em;
        this.id = id;
    }

    public LoggedInUser(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
