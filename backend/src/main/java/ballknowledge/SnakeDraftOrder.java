package ballknowledge;

import java.util.ArrayList;

/**
 * Manages the order of a snake draft.
 *
 * @author Micale
 */
public class SnakeDraftOrder {
    private final ArrayList<Team> draftOrder;
    private int currentPick;
    private final int numOfRounds;
    private final int numOfTeams;

    /**
     * Creates a snake draft order.
     * @param teams the list of teams participating in the draft
     * @param numOfRounds the number of rounds for the draft
     */
    public SnakeDraftOrder(ArrayList<Team> teams, int numOfRounds) {
        this.numOfRounds = numOfRounds;
        this.numOfTeams = teams.size();
        this.currentPick = 0;
        this.draftOrder = new ArrayList<>();
        buildDraftOrder(teams);
    }

    /**
     * Creates the complete snake draft order.
     * @param teams the list of participating teams
     */
    private void buildDraftOrder(ArrayList<Team> teams) {
        for (int round = 1; round <= numOfRounds; round++) {
            if (round % 2 == 1) {//odd rounds
                for (Team team : teams) {
                    draftOrder.add(team);
                }
            } else {
                for (int i = teams.size() - 1; i >= 0; i--) {//even rounds
                    draftOrder.add(teams.get(i));
                }
            }
        }
    }

    /**
     * Returns the team currently drafting.
     * @return the current team drafting
     */
    public Team getCurrentTeam() {
        if (isDraftComplete()) {
            return null;
        }
        return draftOrder.get(currentPick);
    }

    /**
     * Advances the draft to the next pick.
     */
    public void nextPick() {
        if (canAdvance()) {
            currentPick++;
        }
    }

    /**
     * Moves the draft back one pick.
     */
    public void previousPick() {
        if (canGoBack()) {
            currentPick--;
        }
    }

    /**
     * Returns whether the draft can advance to another pick.
     * @return true if there is another pick available, false otherwise
     */
    public boolean canAdvance() {
        return currentPick < draftOrder.size() - 1;
    }

    /**
     * Returns whether the draft can move back to a previous pick.
     * @return true if a previous pick exists, false otherwise
     */
    public boolean canGoBack() {
        return currentPick > 0;
    }

    /**
     * Returns the current overall pick number.
     * Pick numbers start at 1.
     * @return overall pick
     */
    public int getOverallPick() {
        return currentPick + 1;
    }

    /**
     * Returns the current round number.
     * @return the current round
     */
    public int getCurrentRound() {
        return (currentPick / numOfTeams) + 1;
    }

    /**
     * Returns true if the draft is at the first pick.
     * @return true if first pick
     */
    public boolean isFirstPick() {
        return currentPick == 0;
    }

    /**
     * Returns true if the draft is at the final pick.
     * @return true if final pick
     */
    public boolean isLastPick() {
        return currentPick == draftOrder.size() - 1;
    }

    /**
     * Returns true if draft has reached the final pick.
     * @return draft completion status
     */
    public boolean isDraftComplete() {
        return currentPick >= draftOrder.size() -1;
    }

    /**
     * Returns total number of picks.
     * @return total picks
     */
    public int getTotalPicks() {
        return draftOrder.size();
    }

    /**
     * Returns the complete draft order.
     * @return the draft order
     */
    public ArrayList<Team> getDraftOrder() {
        return draftOrder;
    }
}