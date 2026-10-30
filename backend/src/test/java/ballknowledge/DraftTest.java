package ballknowledge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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

}
