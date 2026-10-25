import java.util.ArrayList;

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
    private double startingSalary;
    private ArrayList<Team> teams = new ArrayList<>();

    /**
     * Create a default draft with 10 rounds, three teams, and a starting budget of 500 million dollars
     */
    public Draft(){
        numOfRounds = 10;
        numOfTeams = 3;
        startingSalary = 500000000;
    }

    /**
     * Creates a draft with specified parameters
     * @param numOfTeams the number of teams in the draft
     * @param numOfRounds the number of rounds in the draft
     * @param startingSalary the starting budget each team will have
     */
    public Draft(int numOfTeams, int numOfRounds, double startingSalary){
        this.numOfTeams = numOfTeams;
        this.numOfRounds = numOfRounds;
        this.startingSalary = startingSalary;
    }

    /**
     * Creates the table for the draft based on the number of teams and rounds.
     * Each row besides the first row represents the round number, ex. 1 = round 1
     * Each column besides the first column represents each team's pick within the draft, ex 1 = team 1
     * The first row and column are headers for the round number and team names
     */
    public void createDraftTable() {
        if (!teams.isEmpty()) {
            if (numOfRounds > 0 && numOfTeams > 0) {
                draft = new String[numOfRounds + 1][numOfTeams + 1];
                draft[0][0] = "Round";
                for (int col = 1; col <= numOfTeams; col++) {
                    draft[0][col] = teams.get(col - 1).getName();
                }
                for (int row = 1; row <= numOfRounds; row++) {
                    draft[row][0] = String.valueOf(row);
                }
            }
        }
    }

    /**
     * Creates default teams based on the number of teams wanted for the draft. ex. Team 1, Team 2, Team 3
     */
    public void createTeams(){
        if(numOfTeams > 0){
        for (int i = 1; i < numOfTeams+1 ; i++) {
            Team team = new Team();
            team.setName("Team "+ i);
            team.setBudget(startingSalary);
            teams.add(i-1,team);
            }
        }
    }

    /**
     * Prints the names of all the current teams.
     */
    public void getTeamNames(){
        for (int i = 0; i < teams.size() ; i++) {
            System.out.println(teams.get(i));
        }
    }

}