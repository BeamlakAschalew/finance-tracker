package model;

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
