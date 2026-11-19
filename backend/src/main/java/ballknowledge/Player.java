package ballknowledge;

import java.util.List;

/**
 * Implementation of a player class that builds a "player" based on a dataset without relying on raw rows.
 *
 * @author Micale
 */
public class Player{
    private int ID;
    private String name;
    private String pos;
    private double salary;

    /**
     *A constructor for a player with the following parameters
     * @param ID the id of a player
     * @param name the name of the player
     * @param pos the position the player plays
     * @param sal the cost of a player
     */
    public Player(int ID, String name, String pos, double sal) {
        this.ID = ID;
        this.name = name;
        this.pos = pos;
        this.salary = sal;
    }

    /**
     *An empty constructor
     */
    public Player(){}

    /**
     *A boolean method that looks up a player by their ID and copies their ID, name, position, and salary to the private methods of this class.
     * @param ID the ID the user wants to search for
     * @return true if the search was successful, false otherwise.
     */
    public boolean findPlayerByID(int ID) {
        if (Importer.getAvailableRoster() != null) {
            int idIndex = Importer.availableRoster.getColumns().indexOf("ID"); //Gets the column index for the ID column
            int nameIndex = Importer.availableRoster.getColumns().indexOf("Player"); //Gets the column index for the Name column
            int posIndex = Importer.availableRoster.getColumns().indexOf("Pos"); //Gets the column index for the Position column
            int salaryIndex = Importer.availableRoster.getColumns().indexOf("Salary"); //Gets the column index for the Salary column

            for (List<Object> row : Importer.availableRoster.getRows()) {
                int id = Integer.parseInt(row.get(idIndex).toString()); //Checks the ID column for the ID of the player we're looking for

                if (id == ID) {//Checks whether the ID is valid or not
                    this.ID = id;
                    this.name = row.get(nameIndex).toString();//Gets the name of a player
                    this.pos = row.get(posIndex).toString();//Gets the position of a player
                    this.salary = Double.parseDouble(row.get(salaryIndex).toString()); //Gets the cost of a player
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *Returns the ID of this player.
     * @return the ID of this player
     */
    public int getID() {
        return ID;
    }

    /**
     *Returns the name of the player
     * @return the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     *Returns the position the player plays
     * @return the name of the position the player plays
     */
    public String getPos(){
        return pos;
    }

    /**
     *Returns the salary/cost of a player.
     * @return the salary/cost of a player
     */
    public Double getSalary(){
        return salary;
    }
}