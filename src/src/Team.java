/**
 *Creates a team to be changed and managed by the draft
 *
 * @author Micale
 */
public class Team {
    private String name; //name of team
    private double budget; //budget of team
    private Player[] players; //array of players on team

    /**
     * Sets the name of the team
     * @param name the name of team
     */
    void setName(String name){
        this.name = name;
    }

    /**
     * Returns the name of the team as a string.
     * @return the name of the team
     */
    String getName(){
        return name;
    }

    /**
     * Sets the budget for the team.
     * @param budget the starting budget of the team.
     */
    void setBudget(double budget){
        this.budget= budget;
    }

    /**
     * Changes the budget
     * @param amt the amount the budget should change by
     */
    void changeBudget(double amt){
        budget += amt;
    }

    /**
     * Returns the current budget of the team as a double value.
     * @return the current budget of the team.
     */
    double getBudget(){
        return budget;
    }

    void addPlayer(){

    }
}
