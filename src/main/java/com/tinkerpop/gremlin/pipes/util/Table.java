package com.tinkerpop.gremlin.pipes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class Table implements Iterable<Table.Row> {

    private final List<Row> table = new ArrayList<Row>();
    private List<String> columnNames;
    private int tableWidth = -1;

    public Table() {
        this.columnNames = new ArrayList<String>();
    }

    public Table(String... columnNames) {
        this.columnNames = Arrays.asList(columnNames);
        this.tableWidth = columnNames.length;
    }

    public void addRow(List row) {
        if (this.tableWidth == -1) {
            this.tableWidth = row.size();
        } else {
            if (row.size() != tableWidth) {
                throw new RuntimeException("Table width is " + this.tableWidth + " and row width is " + row.size());
            }
        }
        this.table.add(new Row(row));
    }

    public void addRow(Object... row) {
        this.addRow(Arrays.asList(row));
    }

    public void setColumnNames(String... columnNames) {
        if (tableWidth != -1 && columnNames.length != tableWidth) {
            throw new RuntimeException("Table width is " + this.tableWidth + " and there are " + columnNames.length + " column names");
        }
        this.columnNames = Arrays.asList(columnNames);
    }

    public int getRowCount() {
        return this.table.size();
    }

    public int getColumnCount() {
        return tableWidth;
    }

    public Object get(final int row, final int column) {
        return this.table.get(row).get(column);
    }

    public Object get(final int row, final String columnName) {
        return this.table.get(row).get(this.columnNames.indexOf(columnName));
    }

    public Row getRow(final int row) {
        return this.table.get(row);
    }

    public List getColumn(final int column) {
        final List temp = new ArrayList();
        for (final Row row : this.table) {
            temp.add(row.get(column));
        }
        return temp;
    }

    public List getColumn(final String columnName) {
        return this.getColumn(this.columnNames.indexOf(columnName));
    }

    public Iterator<Row> iterator() {
        return this.table.iterator();
    }

    public String toString() {
        return this.table.toString();
    }

    public void clear() {
        this.tableWidth = -1;
        this.table.clear();
    }

    protected class Row extends ArrayList {

        public Row(final List row) {
            super(row);
        }

        public String toString() {

            final StringBuffer buffer = new StringBuffer("[");
            for (int i = 0; i < this.size(); i++) {
                if (columnNames.size() > 0) {
                    buffer.append(columnNames.get(i));
                    buffer.append(":");
                }
                buffer.append(this.get(i));
                if (i < this.size() - 1)
                    buffer.append(", ");
            }
            buffer.append("]");
            return buffer.toString();

        }

        public Object getColumn(String columnName) {
            return this.get(columnNames.indexOf(columnName));
        }
    }
}
