package model;

public class Transaction {
    public int txnId, accountId, categoryId;
    public double amount;
    public String notes, type, date;

    public Transaction(int tid, int acId, int cId, double am, String nt, String ty, String dt) {
        txnId = tid;
        accountId = acId;
        categoryId = cId;
        amount = am;
        notes = nt;
        type = ty;
        date = dt;
    }
}
