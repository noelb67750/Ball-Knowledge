package ballknowledge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {

    @Test
    void constructor(){
        DataFrame df = new DataFrame();

        assertNotNull(df.getColumns());
        assertNotNull(df.getRows());
        assertTrue(df.getColumns().isEmpty());
        assertTrue(df.getRows().isEmpty());
    }

    @Test
    void getColumns() {
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        df.addColumn("Position");

        assertEquals(2, df.getColumns().size());
        assertEquals("Name", df.getColumns().get(0));
        assertEquals("Position", df.getColumns().get(1));
    }

    @Test
    void setColumns() {
        DataFrame df = new DataFrame();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Name");
        columns.add("Position");
        df.setColumns(columns);

        assertEquals(columns, df.getColumns());
    }

    @Test
    void addColumn() {
        DataFrame df = new DataFrame();
        df.addColumn("Team");

        assertEquals(1, df.getColumns().size());
        assertEquals("Team", df.getColumns().get(0));
    }

    @Test
    void getRows() {
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        List<Object> row = new ArrayList<>();
        row.add("LeBron");
        df.addRow(row);

        assertEquals(1, df.getRows().size());
        assertEquals("LeBron", df.getRows().get(0).get(0));
    }

    @Test
    void setRow() {
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        df.addColumn("Position");
        List<Object> row1 = new ArrayList<>();
        row1.add("LeBron");
        row1.add("SF");
        df.addRow(row1);
        List<Object> row2 = new ArrayList<>();
        row2.add("Curry");
        row2.add("PG");
        df.setRow(row2, 0);

        assertEquals("Curry", df.getRows().get(0).get(0));
        assertEquals("PG", df.getRows().get(0).get(1));
    }

    @Test
    void setRowException(){
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        df.addColumn("Position");
        List<Object> badRow = new ArrayList<>();
        badRow.add("LeBron");

        assertThrows(IllegalArgumentException.class, () -> df.addRow(badRow));
    }

    @Test
    void addRow() {
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        df.addColumn("Position");
        List<Object> row = new ArrayList<>();
        row.add("Durant");
        row.add("SF");
        df.addRow(row);

        assertEquals(1, df.getRows().size());
        assertEquals("Durant", df.getRows().get(0).get(0));
        assertEquals("SF", df.getRows().get(0).get(1));
    }

    @Test
    void addRowException(){
        DataFrame df = new DataFrame();
        df.addColumn("Name");
        df.addColumn("Position");
        List<Object> validRow = new ArrayList<>();
        validRow.add("LeBron");
        validRow.add("SF");
        df.addRow(validRow);
        List<Object> badRow = new ArrayList<>();
        badRow.add("Curry");

        assertThrows(IllegalArgumentException.class, () -> df.setRow(badRow, 0));
    }
}