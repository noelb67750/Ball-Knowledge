package ballknowledge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Team one;

    @BeforeEach
    void createDF(){
        DataFrame roster = new DataFrame();
        roster.addColumn("ID");
        roster.addColumn("Name");
        roster.addColumn("Salary");
        roster.addColumn("OVR");
        roster.addColumn("Taken");
        List<Object> player1 = new ArrayList<>();
        player1.add(1);
        player1.add("LeBron James");
        player1.add(50.0);
        player1.add(80);
        player1.add(false);
        List<Object> player2 = new ArrayList<>();
        player2.add(2);
        player2.add("Stephen Curry");
        player2.add(40.0);
        player2.add(85);
        player2.add(true);
        List<Object> player3 = new ArrayList<>();
        player3.add(3);
        player3.add("Kevin Durant");
        player3.add(60.0);
        player3.add(75);
        player3.add(false);
        List<Object> player4 = new ArrayList<>();
        player4.add(-1);
        player4.add("Jane Doe");
        player4.add(0);
        player4.add(0);
        player4.add(false);
        roster.addRow(player1);
        roster.addRow(player2);
        roster.addRow(player3);
        roster.addRow(player4);
        Importer.setAvailableRoster(roster);
        one = new Team();
    }

    @AfterEach
    void deleteDF(){
        Importer.setAvailableRoster(null);
        one = null;
    }

    @Test
    void setAndgetName() {
        one.setName("Number 1");

        assertEquals("Number 1", one.getName());
    }


    @Test
    void setAndgetBudget() {
        one.setBudget(100.12);

        assertEquals(100.12, one.getBudget());
    }

    @Test
    void setAndgetBudgetNegative() {
        one.setBudget(-1000);

        assertNotEquals(-1000, one.getBudget());
    }

    @Test
    void changeBudgetPositive() {
        one.setBudget(1000);
        one.changeBudget(500);

        assertEquals(1500, one.getBudget());
    }

    @Test
    void changeBudgetNegative() {
        one.setBudget(1000);
        one.changeBudget(-500);

        assertEquals(500, one.getBudget());
    }

    @Test
    void changeBudgetOutofBounds() {
        one.setBudget(1000);
        one.changeBudget(-5000);

        assertEquals(1000, one.getBudget());
    }

    @Test
    void draftPlayerTrue() {
        one.setBudget(1000);

        assertTrue(one.draftPlayer(1));
        assertEquals(1, one.getPlayers().getRows().size());

        int takenIndex = Importer.getAvailableRoster().getColumns().indexOf("Taken");

        assertEquals(true, Importer.getAvailableRoster().getRows().get(0).get(takenIndex));
    }

    @Test
    void draftPlayerInvalidID(){
        one.setBudget(10000);

        assertFalse(one.draftPlayer(542));
        assertEquals(0, one.getPlayers().getRows().size());
    }

    @Test
    void draftPlayerFilledInvalidID(){
        one.setBudget(10000);

        assertFalse(one.draftPlayer(-1));
        assertEquals(0, one.getPlayers().getRows().size());
    }

    @Test
    void draftPlayerInsufficientBudget(){
        one.setBudget(1);

        assertFalse(one.draftPlayer(1));
        assertEquals(0, one.getPlayers().getRows().size());
    }

    @Test
    void draftPlayerAlreadyTaken(){
        one.setBudget(10000);

        assertFalse(one.draftPlayer(2));
        assertEquals(0, one.getPlayers().getRows().size());
    }

    @Test
    void draftPlayerNullRoster(){
        Importer.setAvailableRoster(null);

        assertFalse(one.draftPlayer(1));
    }

    @Test
    void removePlayerTrue() {
        one.setBudget(10000);
        one.draftPlayer(1);
        one.removePlayer(1);
        assertEquals(0, one.getPlayers().getRows().size());

        int takenIndex = Importer.getAvailableRoster().getColumns().indexOf("Taken");

        assertEquals(false, Importer.getAvailableRoster().getRows().get(0).get(takenIndex));
    }

    @Test
    void removePlayerNotOnTeam(){
        assertFalse(one.removePlayer(1));
    }

    @Test
    void removePlayerInvalidID(){
        assertFalse(one.removePlayer(123));
    }

    @Test
    void removePlayerNullRoster(){
        Importer.setAvailableRoster(null);

        assertFalse(one.removePlayer(1));
    }

    @Test
    void removePlayerBudgetRefund(){
        one.setBudget(1000);
        one.draftPlayer(1);
        one.removePlayer(1);
        assertEquals(1000, one.getBudget());
    }

    @Test
    void getPlayers() {
        assertNotNull(one.getPlayers());
        assertEquals(5, one.getPlayers().getColumns().size());
        assertEquals(0, one.getPlayers().getRows().size());
    }

    @Test
    void setTeamOVR(){
        one.setBudget(10000);
        one.draftPlayer(1);
        one.draftPlayer(2);
        one.draftPlayer(3);
        one.setTeamOVR();
        int expected = ((80+75)/2);
        assertEquals(expected, one.getTeamOVR());
    }
}