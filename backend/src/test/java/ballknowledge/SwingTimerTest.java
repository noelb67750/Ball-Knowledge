package ballknowledge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwingTimerTest {
    //Display Tests
    @Test
    void validTimeDisplay() {
        SwingTimer swingTimer = new SwingTimer(2, 0, true);
        assertEquals(2, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(120, swingTimer.getTotalSeconds());
    }

    @Test
    void NegInvalidTimeDisplay() {
        SwingTimer swingTimer = new SwingTimer(-1, -120, true);
        assertEquals(0, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(0, swingTimer.getTotalSeconds());
    }

    @Test
    void ZeroTimeDisplay() {
        SwingTimer swingTimer = new SwingTimer(0, 0, true);
        assertEquals(0, swingTimer.getMinutes());
        assertEquals(0, swingTimer.getSeconds());
        assertEquals(0, swingTimer.getTotalSeconds());
    }

    //Independent Methods Tests
    @Test
    void validTimeStartAndStop() {
        SwingTimer swingTimer = new SwingTimer(2, 0, false);
        assertFalse(swingTimer.isRunning());
        swingTimer.startTimer();
        assertTrue(swingTimer.isRunning());
        swingTimer.pauseTimer();
        assertFalse(swingTimer.isRunning());
    }

    @Test
    void changeTime() {
        SwingTimer swingTimer = new SwingTimer(2, 0, false);
        swingTimer.changeTime(1, 30);
        assertEquals(1, swingTimer.getMinutes());
        assertEquals(30, swingTimer.getSeconds());
        assertEquals(90, swingTimer.getTotalSeconds());
    }

    @Test
    void resetTime() {
        SwingTimer swingTimer = new SwingTimer(2, 0, false);
        swingTimer.startTimer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(swingTimer.getTotalSeconds() < 120);
        swingTimer.resetTimer();
        assertEquals(120, swingTimer.getTotalSeconds());
    }
}