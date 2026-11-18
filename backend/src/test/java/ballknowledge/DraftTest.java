package ballknowledge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DraftTest {

    /** Test that a new draft starts in SETUP status */
    @Test

    void newDraftStartsInSetupStatus() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        assertEquals(DraftStatus.SETUP, draft.getStatus());
        assertEquals("TEST1", draft.getDraftCode());
    }
    /**
     * Test that creating a draft with less than 2 teams throws an exception
     */
    @Test
    void numTeamsLessThanTwoThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Draft(1, 10, 500_000_000.0, "TEST1")
        );
    }
    /**
     * Test that creating a draft with 2 teams is allowed
     */
    @Test
    void twoTeamsIsAllowed() {
        Draft draft = new Draft(2, 10, 500_000_000.0, "TEST1");
        assertEquals(DraftStatus.SETUP, draft.getStatus());
    }
    /**
     * Test that creating a draft with a negative budget throws an exception
     */
    @Test
    void negativeBudgetThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Draft(3, 10, -500_000_000.0, "TEST1")
        );
    }
    /**
     * Test that starting and finishing a draft transitions it to COMPLETE status
     */
    @Test
    void startThenFinishTransitionsToComplete() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        draft.start();
        assertEquals(DraftStatus.IN_PROGRESS, draft.getStatus());
        draft.finish();
        assertEquals(DraftStatus.COMPLETE, draft.getStatus());
    }
    /**
     * Test that starting a draft twice throws an exception
     */
    @Test
    void callingStartTwiceThrowsIllegalStateException() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        draft.start();
        assertThrows(IllegalStateException.class, draft::start);
    }
    /**
     * Test that finishing a draft before starting it throws an exception
     */
    @Test
    void finishBeforeStartThrowsIllegalStateException() {
        Draft draft = new Draft(3, 10, 500_000_000.0, "TEST1");
        assertThrows(IllegalStateException.class, draft::finish);
    }

}
