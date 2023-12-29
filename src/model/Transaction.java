package model;

public class Transaction {
    public int txnId, accountId, categoryId;
    public double amount;
    public String notes, type, date;

    public Transaction(int tid, int acId, int cId, double am, String nt, String ty, String dt) {
        txnId = tid;
        acId = accountId;
        cId = categoryId;
        amount = am;
        nt = notes;
        ty = type;
        dt = date;

    }
}
