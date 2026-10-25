import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Imports a file and transforms it into a dataframe.
 *
 * @author Micale
 */
public class Importer {
    private static final String DATA_FOLDER = "savedFiles";
    public static DataFrame availableRoster;

    /**
     * Returns the available roster that will be used for the draft.
     * @return the available roster that will be used for the draft
     */
    public static DataFrame getAvailableRoster() {
        return availableRoster;
    }

    /**
     * Sets the dataframe that will be used for the draft.
     * @param df the data frame that will be used for the draft
     */
    public static void setAvailableRoster(DataFrame df) {
        availableRoster = df;
    }

    /**
     * Creates a storage folder for all the files that the user has imported.
     * It also creates one if it doesn't exist.
     */
    public static void initialize() {
        try {
            Files.createDirectories(Paths.get(DATA_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports a file from the user's computer and saves it.
     * Only allows csv, tsv, or xlsx files.
     */
    public static void importFile() {
        JFileChooser chooser = new JFileChooser(); //Creates a fle explorer
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Importable Files", "csv", "tsv", "xlsx"); //Crates the filter
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(null); //Opens the file picker window and waits on user
        if (result == JFileChooser.APPROVE_OPTION) { //Checks if user picked a file
            File selected = chooser.getSelectedFile();
            try {
                Path destination = Paths.get(DATA_FOLDER, selected.getName()); //Builds where the imported file will be stored
                Files.copy(selected.toPath(), destination, StandardCopyOption.REPLACE_EXISTING); //Copies the imported file into the storage folder
                JOptionPane.showMessageDialog(null, "File imported successfully."); //Popup message if the file was imported or not
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a string of all the names of files in the savedFiles folder
     */
    public static void getNamedSavedFiles() {
        File folder = new File(DATA_FOLDER); //Creates File object that represents the folder containing all the saved files
        File[] files = folder.listFiles(); //Gets all the files in the folder
        if (files.length == 0) {
            System.out.print("No files saved"); //Prints out a message if the folder is empty
        } else { //If the folder isn't empty, runs a for loop to get the names of all the files in the folder
            //One thing to note is that the names of each file also contains the path of each file. ex. savedFiles\<name of file>
            String saved = files[0].toString();
            for (int i = 1; i < files.length ; i++) {
                saved += ", " + files[i].toString();
            }
            System.out.println("Files Saved:");
            System.out.println(saved);
        }
    }

    /**
     * Return all saved files.
     */
    public static File[] getSavedFiles() {
        File folder = new File(DATA_FOLDER); //Creates File object that represents the folder containing all the saved files
        File[] files = folder.listFiles(); //Gets all the files in the folder
        if (files == null) {
            return new File[0]; //Returns an empty file array, ex. []
        }
        return files; //Returns array of files found
    }

    /**
     * Let the user choose a saved file.
     */
    public static File chooseSavedFile() {
        File[] files = getSavedFiles(); //Creates array of files found
        if (files.length == 0) { //If the length of the array is zero, print popup messages and error messages that a file needs to be imported
            JOptionPane.showMessageDialog(null, "No saved files found.");
            System.out.println("Please import a file to begin.");
            return null;
        }
        //Creates an array that will hold only file names. It does this by turning the file array into a stream to be processed where each file's name is extracted and turned back into a String array
        String[] names = Arrays.stream(files).map(File::getName).toArray(String[]::new);
        //Creates a variable to store the file name the user chooses
        String selected = (String) JOptionPane.showInputDialog(null, "Select a file", "Saved Files", JOptionPane.PLAIN_MESSAGE, null, names, names[0]);
        if (selected == null) {
            return null;
        }
        return new File(DATA_FOLDER, selected); //Returns selected file
    }

    /**
     * Delete a saved file.
     */
    public static void deleteFile() {
        File file = chooseSavedFile(); //Chooses a file to delete
        if (file == null) {
            return; //If null exit out of method
        }
        //Opens a confirmation window and stores the result, and asks whether the user wants to delete the file or not
        int confirm = JOptionPane.showConfirmDialog(null, "Delete " + file.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (file.delete()) {
                JOptionPane.showMessageDialog(null, "File deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "Could not delete file.");
            }
        }
    }

    /**
     *Loads a data frame based off a file.
     * @param file the file to be used as the data frame for the draft
     * @return the transformed file as a data frame
     */
    public static DataFrame loadDataFrame(File file) {
            try {
                ProcessBuilder pb = new ProcessBuilder("python", "src/dataframe_converter.py", file.getAbsolutePath()); //Launches the Python program
                Process process = pb.start(); //Runs the Python program
                int exitCode = process.waitFor(); //Waits for Python program to finish running
                if(exitCode != 0){ //Checks if the Python program was able to run successfully or not
                    throw new RuntimeException("Python script failed."); //Throws exception if it was unsuccessful
                }
                return readDataFrameFromCSV(new File("current_dataframe.csv")); //Runs method below to transform and crate the data frame
            } catch (Exception e) {
                e.printStackTrace();
                return null; //Returns null if the data frame failed to be loaded
            }
        }

    /**
     * Reads a CSV file and converts it into a java DataFrame
     * @param file the file to be turned into a data frame in java
     * @return the data frame
     */
    private static DataFrame readDataFrameFromCSV(File file) {
        DataFrame df = new DataFrame();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { //Opens the file so Java can read it
            String line;
            boolean firstRow = true; //Tracks if the header row is being read or not
            while ((line = reader.readLine()) != null) { //Reads the file one line at a time
                String[] values = line.split(","); //Splits the line at every comma
                if (firstRow) { //First row becomes the columns of the data frame
                    for (String value : values) {
                        df.getColumns().add(value);
                    }
                    firstRow = false; //Changes firstRow to false so the every row after is tracked as not being the header
                } else {
                    ArrayList<Object> row = new ArrayList<>();
                    for (String value : values) { //Loops through every value in the row
                        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                            row.add(Boolean.parseBoolean(value)); //Turns the Taken column from String to boolean
                        } else {
                            row.add(value); //For other values, simply add it to the row
                        }
                    }
                    df.getRows().add(row); //Adds completed row to the dataframe
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return df;
    }
}


