package model;

public class UserInfo {
    public String fname, lname, username, email, created_at;
    public int id;

    public UserInfo(String fname, String lname, String username, String email, String created_at, int id) {
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.username = username;
        this.created_at = created_at;
        this.email = email;
    }
}
