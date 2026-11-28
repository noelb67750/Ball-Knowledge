package ballknowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates a dataframe using arrays.
 *
 * @author Micale
 */
public class DataFrame {
    private ArrayList<String> columns; //The columns in the dataframe
    private ArrayList<List<Object>> rows; //The rows in the dataframe

    /**
     * Initializes the dataframe with rows and columns.
     */
    public DataFrame() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }

    /**
     * Returns the columns/headers of the dataframe.
     * @return the columns of the dataframe
     */
    public ArrayList<String> getColumns() {
        return columns;
    }

    /**
     * Sets the columns of the dataframe based on an array of strings.
     * @param columns the array of strings you want the column to be
     */
    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    /**
     * Adds a column to the end of the dataframe.
     * @param column the name of the column
     */
    public void addColumn(String column) {
        columns.add(column);
    }

    /**
     * Returns all the rows of a dataframe.
     * @return the rows of a dataframe
     */
    public ArrayList<List<Object>> getRows() {
        return rows;
    }

    /**
     * Sets the row of the dataframe based on an array of strings.
     * @param rows the array of strings you want the row to be
     * @param num the row number you want the row to be set
     */
    public void setRow(List<Object> rows, int num){
        if (rows.size() != columns.size()) {
            throw new IllegalArgumentException("Row size must match number of columns");
        }
        this.rows.set(num, rows);
    }

    /**
     * Adds a row to the dataframe based off the number of columns.
     * @param row the list of objects to be added to the row
     */
    public void addRow(List<Object> row) {
        if (row.size() != columns.size()) {
            throw new IllegalArgumentException("Row size must match number of columns");
        }
        rows.add(row);
    }
}

