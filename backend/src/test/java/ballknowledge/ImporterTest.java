package ballknowledge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImporterTest {

    private static final String DATA_FOLDER = "savedFiles";

    @BeforeEach
    void setup() throws Exception {
        Files.createDirectories(Path.of(DATA_FOLDER));
        File folder = new File(DATA_FOLDER);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @AfterEach
    void cleanup() throws Exception {
        File folder = new File(DATA_FOLDER);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @org.junit.jupiter.api.Test
    void setAndgetAvailableRoster() {
        DataFrame df = new DataFrame();
        Importer.setAvailableRoster(df);

        assertSame(df, Importer.getAvailableRoster());
    }

    @org.junit.jupiter.api.Test
    void initialize() throws IOException {
        Path folderPath = Path.of(DATA_FOLDER);
        Files.deleteIfExists(folderPath);

        assertFalse(Files.exists(folderPath));

        Importer.initialize();
        File folder = new File(DATA_FOLDER);

        assertTrue(Files.exists(folderPath));
        assertTrue(Files.isDirectory(folderPath));
    }

    @org.junit.jupiter.api.Test
    void getSavedFilesEmpty() {
        File[] files = Importer.getSavedFiles();

        assertNotNull(files);
        assertEquals(0, files.length);
    }

    @org.junit.jupiter.api.Test
    void getSavedFilesNull() {
        File[] files = null;
        files = Importer.getSavedFiles();

        assertNotNull(files);
        assertEquals(0, files.length);
    }

    @org.junit.jupiter.api.Test
    void getSavedFilesNotEmpty() {
        File test1 = new File(DATA_FOLDER, "file1.csv");
        File test2 = new File(DATA_FOLDER, "file2.csv");
        try {
            test1.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            test2.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File[] files = Importer.getSavedFiles();

        assertEquals(2, files.length);
    }

    @org.junit.jupiter.api.Test
    void readDataFrameFromCSV() throws Exception {
        File csv = File.createTempFile("test", ".csv");
        try (FileWriter writer = new FileWriter(csv)) {
            writer.write("Name,Position,Taken\n");
            writer.write("Josh,PG,true\n");
            writer.write("James,SF,false\n");
        }
        Method method = Importer.class.getDeclaredMethod("readDataFrameFromCSV", File.class);
        method.setAccessible(true);
        DataFrame df = (DataFrame) method.invoke(null, csv);

        assertEquals(3, df.getColumns().size());
        assertEquals("Name", df.getColumns().get(0));
        assertEquals("Position", df.getColumns().get(1));
        assertEquals("Taken", df.getColumns().get(2));
        assertEquals(2, df.getRows().size());
        assertEquals("Josh", df.getRows().get(0).get(0));
        assertEquals("PG", df.getRows().get(0).get(1));
        assertEquals(true, df.getRows().get(0).get(2));
        assertEquals("James", df.getRows().get(1).get(0));
        assertEquals("SF", df.getRows().get(1).get(1));
        assertEquals(false, df.getRows().get(1).get(2));
    }

    @org.junit.jupiter.api.Test
    void readDataFrameFromCSVEmpty() throws Exception {
        File csv = File.createTempFile("empty", ".csv");
        Method method = Importer.class.getDeclaredMethod("readDataFrameFromCSV", File.class);
        method.setAccessible(true);
        DataFrame df = (DataFrame) method.invoke(null, csv);

        assertNotNull(df);
        assertTrue(df.getColumns().isEmpty());
        assertTrue(df.getRows().isEmpty());
    }
}
