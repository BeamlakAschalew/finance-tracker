package model;

import java.time.LocalDate;

// This whole entire class' use is to contain the data for a custom filter and to generate a custom SQL query
public class InputQuery {
    public String query, notes, type;
    public int accId, catId;
    public LocalDate date;
    public double amount;
    public boolean dateProvided, noteProvided;

    public InputQuery(boolean dt, boolean note, String q, String notes, String type, int accId, int catId, LocalDate date, double amount) {
        query = q;
        this.dateProvided = dt;
        this.noteProvided = note;
        this.catId = catId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.notes = notes;
    }
}
