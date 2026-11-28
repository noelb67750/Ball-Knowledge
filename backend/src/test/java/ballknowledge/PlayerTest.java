package ballknowledge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void createDF() {
        DataFrame roster = new DataFrame();
        roster.addColumn("ID");
        roster.addColumn("Player");
        roster.addColumn("Pos");
        roster.addColumn("Salary");
        roster.addColumn("Taken");
        List<Object> player1 = new ArrayList<>();
        player1.add(1);
        player1.add("LeBron James");
        player1.add("PF");
        player1.add(50.0);
        player1.add(false);
        List<Object> player2 = new ArrayList<>();
        player2.add(2);
        player2.add("Stephen Curry");
        player2.add("PG");
        player2.add(40.0);
        player2.add(true);
        List<Object> player3 = new ArrayList<>();
        player3.add(-1);
        player3.add("John Doe");
        player3.add("NA");
        player3.add(0);
        player3.add(false);
        roster.addRow(player1);
        roster.addRow(player2);
        roster.addRow(player3);
        Importer.setAvailableRoster(roster);
    }

    @Test
    void findPlayerByID() {
        Player p = new Player();
        assertTrue(p.findPlayerByID(1));
    }

    @Test
    void findPlayerByIDNotinDF() {
        Player p = new Player();
        assertFalse(p.findPlayerByID(4));
    }

    @Test
    void findPlayerByInvalidID() {
        Player p = new Player();
        assertFalse(p.findPlayerByID(-1));
    }

    @Test
    void getID() {
        Player p = new Player();
        p.findPlayerByID(2);
        assertEquals(2, p.getID());
    }

    @Test
    void getName() {
        Player p = new Player();
        p.findPlayerByID(2);
        assertEquals("Stephen Curry", p.getName());
    }

    @Test
    void getPos() {
        Player p = new Player();
        p.findPlayerByID(1);
        assertEquals("PF", p.getPos());
    }

    @Test
    void getSalary() {
        Player p = new Player();
        p.findPlayerByID(2);
        assertEquals(40, p.getSalary());
    }

    //Testing for null or blank names, positions, salaries, or any other parameter is not needed
    //because the Python code creating the dataframe handles these missing and blank values.
}