import java.util.ArrayList;
import java.util.List;

/**
 *Creates a team to be changed and managed by the draft
 *
 * @author Micale
 */
public class Team {
    private String name; //name of team
    private double budget; //budget of team
    public DataFrame players; //dataframe of players selected

    /**
     * Initializes the team class by creating a new dataframe and setting the
     * headers based on the available roster.
     */
    public Team(){
        players = new DataFrame();
        players.setColumns(Importer.getAvailableRoster().getColumns());
    }

    /**
     * Sets the name of the team
     * @param name the name of team
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the name of the team as a string.
     * @return the name of the team
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the budget for the team.
     * @param budget the starting budget of the team.
     */
    public void setBudget(double budget){
        this.budget= budget;
    }

    /**
     * Changes the budget
     * @param amt the amount the budget should change by
     */
    public void changeBudget(double amt){
        budget += amt;
    }

    /**
     * Returns the current budget of the team as a double value.
     * @return the current budget of the team.
     */
   public double getBudget(){
       return budget;
    }

    /**
     * Drafts a player from the available roster based on if it's a valid ID, the player
     * has already been taken or not, and if the team has enough funds.
     * @param playerID the ID of the player the team wants to draft
     * @return Returns true if the team is able to draft the player, false otherwise
     */
    public boolean draftPlayer(int playerID) {
        int idIndex = Importer.availableRoster.getColumns().indexOf("ID"); //Gets the column index for the ID column
        int takenIndex = Importer.availableRoster.getColumns().indexOf("Taken"); //Gets the column index for the Taken column
        int salaryIndex = Importer.availableRoster.getColumns().indexOf("Salary"); //Gets the column index for the Salary column

        for (List<Object> row : Importer.availableRoster.getRows()) {
            int id = Integer.parseInt(row.get(idIndex).toString()); //Checks the ID column for the ID of the player we're looking for

            if (id == playerID) { //Checks whether the ID is valid or not
                boolean taken = Boolean.parseBoolean(row.get(takenIndex).toString()); //Checks if the player is taken or not
                if (taken) { //Prints an error message and returns false if the player is already taken
                    System.out.println("The player has already been drafted.");
                    return false;
                }
                double salary = Double.parseDouble(row.get(salaryIndex).toString()); //Gets the cost of a player

                if (budget < salary) { //Checks if the team can buy the player or not
                    System.out.println("Your team does not have enough money to select this player."); //Prints an error message if the team cannot
                    return false; //Returns false if the team cannot buy the player
                }
                row.set(takenIndex, true); //Changes the player's taken status to true
                players.getRows().add(new ArrayList<>(row)); //Adds the row to the teams dataframe
                this.changeBudget(-salary); //Updates the teams budget based on the cost of the player
                return true; //Returns true due to a successfully drafting a player
            }
        }
        System.out.println("That ID does not exist."); //Prints an error message since the ID is not valid
        return false; //Returns false due to not being able to draft a player
    }

    /**
     * Removes a player from the team's dataframe of drafted players.
     * @param playerID the ID of the player the team would like to remove
     * @return Returns true if the player was successfully removed and false otherwise
     */
    public boolean removePlayer(int playerID) {
        int rosterIdIndex = players.getColumns().indexOf("ID"); //Gets the column index of the ID column from the team's dataframe of drafted players
        int availableIdIndex = Importer.availableRoster.getColumns().indexOf("ID"); //Gets the column index of the ID column from the available roster
        int takenIndex = Importer.availableRoster.getColumns().indexOf("Taken"); //Gets the column index of the Taken column from the available roster
        int salaryIndex = players.getColumns().indexOf("Salary"); //Gets the column index of the Salary column from the available roster
        for (int i = 0; i < players.getRows().size(); i++) { //For loop to find the player in the team's dataframe to remove
            List<Object> rosterRow = players.getRows().get(i);
            int id = Integer.parseInt(rosterRow.get(rosterIdIndex).toString());
            if (id == playerID) {
                double salary = Double.parseDouble(rosterRow.get(salaryIndex).toString());
                changeBudget(salary); //Refunds the budget of the team based on the salary of the player
                for (List<Object> availableRow : Importer.availableRoster.getRows()) {
                    int availableID = Integer.parseInt(availableRow.get(availableIdIndex).toString());
                    if (availableID == playerID) {
                        availableRow.set(takenIndex, false); //Changes the player's taken status to false
                        break;
                    }
                }
                players.getRows().remove(i); //Removes player from the team's dataframe
                return true; //Returns true if the removal was successful
                }
             }
        return false; //Returns false if the removal was unsuccessful
        }

    /**
     * Prints the columns and rows of the dataframe of players drafted by the team.
     */
    public void getPlayers(){
            System.out.println(players.getColumns());
            System.out.println(players.getRows());
        }
}
