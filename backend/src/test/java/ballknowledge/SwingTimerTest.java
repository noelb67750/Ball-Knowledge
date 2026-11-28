package ballknowledge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwingTimerTest {
    @Test
    void validTime() {
        SwingTimer swingTimer = new SwingTimer(2, 0);
        assertEquals(2, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(120, swingTimer.getTotalSeconds());
    }

    @Test
    void NegInvalidTime() {
        SwingTimer swingTimer = new SwingTimer(-1, -120);
        assertEquals(0, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(0, swingTimer.getTotalSeconds());
    }

    @Test
    void ZeroTime() {
        SwingTimer swingTimer = new SwingTimer(0, 0);
        assertEquals(0, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(0, swingTimer.getTotalSeconds());
    }
}