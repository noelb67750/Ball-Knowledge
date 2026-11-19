package ballknowledge;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class SnakeDraftOrderTest {
    private final Draft draft = new Draft(3,2, 1000);
    private final SnakeDraftOrder SDO = new SnakeDraftOrder(draft.getTeams(), 2);


    @Test
    void getCurrentTeam() {
        assertEquals("Team 1", SDO.getCurrentTeam().getName());
    }

    @Test
    void nextPick() {
        SDO.nextPick();

        assertEquals("Team 2", SDO.getCurrentTeam().getName());
    }

    @Test
    void previousPick() {
        SDO.nextPick();
        SDO.nextPick();
        SDO.nextPick();
        SDO.previousPick();

        assertEquals("Team 3", SDO.getCurrentTeam().getName());
    }

    @Test
    void canAdvance() {
        assertTrue(SDO.canAdvance());
    }

    @Test
    void cantAdvanceAtEnd() {
        while(SDO.canAdvance()){
            SDO.nextPick();
        }
        assertFalse(SDO.canAdvance());
    }

    @Test
    void canGoBack() {
        assertFalse(SDO.canGoBack());
    }

    @Test
    void canGoBackAfterMoving() {
        SDO.nextPick();

        assertTrue(SDO.canGoBack());
    }

    @Test
    void getOverallPick() {
        assertEquals(1, SDO.getOverallPick());

        SDO.nextPick();

        assertEquals(2, SDO.getOverallPick());
    }

    @Test
    void getCurrentRoundStart() {
        assertEquals(1, SDO.getCurrentRound());
    }

    @Test
    void testCurrentRoundTwo() {
        SDO.nextPick();
        SDO.nextPick();
        SDO.nextPick();

        assertEquals(2, SDO.getCurrentRound());
    }

    @Test
    void isFirstPick() {
        assertTrue(SDO.isFirstPick());
    }

    @Test
    void isFirstPickAfterMove() {
        SDO.nextPick();

        assertFalse(SDO.isFirstPick());
    }

    @Test
    void isLastPick() {
        while (SDO.canAdvance()) {
            SDO.nextPick();
        }

        assertTrue(SDO.isLastPick());
    }

    @Test
    void isDraftComplete() {
        while (SDO.canAdvance()) {
            SDO.nextPick();
        }

        assertTrue(SDO.isDraftComplete());
    }

    @Test
    void isNotDraftComplete() {
        assertFalse(SDO.isDraftComplete());
    }

    @Test
    void getTotalPicks() {
        assertEquals(6, SDO.getTotalPicks());
    }

    @Test
    void getDraftOrder() {
        ArrayList<Team> order = SDO.getDraftOrder();

        assertEquals(6, order.size());
        assertEquals(order.get(0), order.get(5));
        assertEquals(order.get(1), order.get(4));
        assertEquals(order.get(2), order.get(3));
    }
}