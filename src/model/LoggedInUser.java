package model;

// the user of this entire class' is to hold the records of the logged in user
public class LoggedInUser {
    public String firstName, lastName, email, username, password;
    public int id;


    boolean isLoggedIn;

    public LoggedInUser(String un, String fn, String ln, String em, boolean loggedIn, int id, String pw) {
        username = un;
        isLoggedIn = loggedIn;
        firstName = fn;
        lastName = ln;
        email = em;
        this.id = id;
        password = pw;
    }

    public LoggedInUser(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
