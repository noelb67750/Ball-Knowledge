package ballknowledge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * DraftTest
 */
public class DraftTest {

    @Test

    void newDraftStartsInSetupStatus() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        assertEquals(DraftStatus.SETUP, draft.getStatus());
        assertEquals("TEST1", draft.getDraftCode());
    }
    @Test
    void numTeamsLessThanTwoThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Draft(1, 10, 500_000_000.0, "TEST1")
        );
    }
    @Test
    void startThenFinishTransitionsToComplete() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        draft.start();
        assertEquals(DraftStatus.IN_PROGRESS, draft.getStatus());
        draft.finish();
        assertEquals(DraftStatus.COMPLETE, draft.getStatus());
    }
    @Test
    void callingStartTwiceThrowsIllegalStateException() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        draft.start();
        assertThrows(IllegalStateException.class, draft::start);
    }
    /**
     * test to check that draft 
     */
    @Test 
    void callFinishBeforeStartException()
    {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        // :: method reference to call finish on draft
        assertThrows(IllegalStateException.class, draft::finish);
    }
    
   /**
    * test to check if the dimensions of the draft table are as intended
    */
    @Test 
    void draftTableCorrectDimensions()
    {
    Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");

    assertArrayEquals(new String[]{"Round", "Team 1", "Team 2", "Team 3"}, draft.getDraftTable()[0]);
    assertEquals(10, draft.getDraftTable().length - 1);
    }
}
