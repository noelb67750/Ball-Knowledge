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
     * Returns all the rows of a dataframe.
     * @return the rows of a dataframe
     */
    public ArrayList<List<Object>> getRows() {
        return rows;
    }
}

