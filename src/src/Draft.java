/**
 * Creates and manages a mock draft
 *
 * @author Micale
 */
public class Draft {
    private int numOfTeams;
    private int numOfRounds;
    private int currentRoundNum;
    private String[][] draft;

    /**
     * Create a default draft with 10 rounds and three teams.
     */
    public Draft(){
        numOfRounds = 10;
        numOfTeams = 3;
    }

    /**
     * Creates a draft with specified parameters
     * @param numOfTeams the number of teams in the draft
     * @param numOfRounds the number of rounds in the draft
     */
    public Draft(int numOfTeams, int numOfRounds){
        this.numOfTeams = numOfTeams;
        this.numOfRounds = numOfRounds;
    }

    /**
     * Creates the table for the draft based on the number of teams and rounds.
     * Each row represents the round number, ex. 0 = round 1
     * Each column represents each team's pick within the draft, ex 0 = team 1
     */
    void createDraftTable(){
        if(numOfRounds > 0 && numOfTeams > 0){
            draft = new String[numOfRounds][numOfTeams];
        }
    }
}