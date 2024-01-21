package model;

import java.time.LocalDate;

public class InputQuery {
    public String query, notes, type;
    public int accId, catId;
    public LocalDate date;
    public double amount;
    public boolean dt, note;

    public InputQuery(boolean dt, boolean note, String q, String notes, String type, int accId, int catId, LocalDate date, double amount) {
        query = q;
        this.dt = dt;
        this.note = note;
        this.accId = accId;
        this.catId = catId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.notes = notes;
    }
}
