package ballknowledge;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Draft draft;

    public static void main(String[] args) {
        Importer.initialize();
        Importer.importFile();
        File file = Importer.chooseSavedFile();
        DataFrame roster = Importer.loadDataFrame(file);
        Importer.setAvailableRoster(roster);
        Importer.getNamedSavedFiles();
        Importer.getSavedFiles();
        if (file != null) {
            Team test = new Team();
            test.setBudget(1000000000);
            test.removePlayer(16010);
            test.getPlayers();
            System.out.println(Importer.getAvailableRoster().getColumns());
            System.out.println(Importer.getAvailableRoster().getRows());
            }

    }
}

