package ballknowledge;

import javax.swing.*;
import java.awt.*;

/**
 * Makes and displays a timer in minutes and seconds.
 * You can start the timer,
 * pause the timer,
 * reset the timer,
 * or change the timer.
 *
 * @author Micale
 */
public class SwingTimer extends JFrame {
    private final JLabel timerLabel;
    private Timer timer;
    private int totalSeconds;
    private int originalSeconds;
    private int minutes;
    private int seconds;

    /**
     * Creates the timer and display.
     * @param minutes how many minutes the timer is
     * @param seconds how many seconds the timer is
     */
    public SwingTimer(int minutes, int seconds) {
        if (0 >= minutes){
            this.minutes = 0;
            minutes = 0;
        } else {
            this.minutes = minutes;
        }
        if(seconds < 0  || seconds > 59) {
            this.seconds = 0;
            seconds = 0;
        } else {
            this.seconds = seconds;
        }

        originalSeconds = minutes * 60 + seconds; //Keeps track of the initial time of the timer in seconds
        totalSeconds = originalSeconds;

        //Display window settings
        setTitle("Timer");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        add(timerLabel, BorderLayout.CENTER);
        updateDisplay();
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton resetButton = new JButton("Reset");
        JButton changeButton = new JButton("Change");
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(changeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //Timer
        timer = new Timer(1000, e -> {
            if (totalSeconds > 0) {
                totalSeconds--;
                updateDisplay();
            } else {//Message for when the timer is over
                timer.stop();
                JOptionPane.showMessageDialog(this, "Time Over");
            }});
        //Buttons
        startButton.addActionListener(e -> timer.start());
        pauseButton.addActionListener(e -> timer.stop());
        resetButton.addActionListener(e -> {
            timer.stop();
            totalSeconds = originalSeconds;
            updateDisplay();
        });
        changeButton.addActionListener(e -> {//Allows the user to change the timer at anytime
            String minutesInput = JOptionPane.showInputDialog(this, "Enter minutes:");
            String secondsInput = JOptionPane.showInputDialog(this, "Enter seconds:");
            try {
                int newMinutes = Integer.parseInt(minutesInput);
                int newSeconds = Integer.parseInt(secondsInput);
                if (newMinutes < 0 || newSeconds < 0 || newSeconds > 59) {
                    throw new NumberFormatException();
                } else {
                    this.minutes = newMinutes;
                    this.seconds = newSeconds;
                }
                timer.stop();
                originalSeconds = newMinutes * 60 + newSeconds;
                totalSeconds = originalSeconds;
                updateDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            }
        });
        setVisible(true);
    }

    /**
     * Updates the timer display.
     */
    private void updateDisplay() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Returns the starting minutes of the timer.
     * @return the starting minutes of the timer
     */
    protected int getMinutes(){
        return minutes;
    }

    /**
     * Returns the starting seconds of the timer.
     * @return the starting seconds of the timer
     */
    protected int getSeconds(){
        return seconds;
    }

    /**
     * Returns how long the timer is in seconds.
     * @return how long the timer is in seconds.
     */
    protected int getTotalSeconds(){
        return totalSeconds;
    }

    /**
     * Runs the timer.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingTimer(-1, 80));
    }
}
