package model;

// the use of this class is to contain the column names and the table data after a read operation from the database
public class Transaction {
    private String[] columnNames;
    private Object[][] tableData;

    public Transaction(String[] columnNames, Object[][] tableData) {
        this.columnNames = columnNames;
        this.tableData = tableData;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getTableData() {
        return tableData;
    }
}
