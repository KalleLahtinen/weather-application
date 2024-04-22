package fi.tuni.prog3.weatherapp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadAndWriteToFileTest {
    private static final String TEST_FILENAME = "test_appstate.json";
    private static final String TEST_DATA = "{\"units\":\"metric\",\"currentTown\":\"Helsinki\",\"history\":[],\"favouriteCities\":[]}";

    private ReadAndWriteToFile fileHandler;

    @BeforeEach
    public void setUp() {
        fileHandler = new ReadAndWriteToFile();
    }

    @Test
    public void testReadFromFile() {
        try {
            // Write test data to a temporary file
            writeFile(TEST_FILENAME, TEST_DATA);

            // Read from the temporary file
            fileHandler.readFromFile(TEST_FILENAME);

            // Check if appState is not null
            assertNotNull(fileHandler.appState);

            // Check if the appState has been correctly initialized
            assertEquals("metric", fileHandler.appState.getUnits());
            assertEquals("Helsinki", fileHandler.appState.getCurrentTown());
            assertEquals(0, fileHandler.appState.getHistory().size());
            assertEquals(0, fileHandler.appState.getFavourites().size());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        } finally {
            // Delete the temporary file
            deleteFile(TEST_FILENAME);
        }
    }

    @Test
    public void testWriteToFile() {
        try {
            // Initialize appState with test data
            ApplicationStateManager appState = new ApplicationStateManager();
            appState.setUnits("metric");
            appState.setCurrentTown("Helsinki");
            appState.setHistory(new ArrayList<>());
            appState.setFavourites(new ArrayList<>());

            // Set appState to fileHandler
            fileHandler.appState = appState;

            // Write appState to file
            fileHandler.writeToFile(TEST_FILENAME);

            // Check if file has been created
            assertTrue(new File(TEST_FILENAME).exists());

            // Read from the written file
            ApplicationStateManager readAppState = readAppStateFromFile(TEST_FILENAME);

            // Check if the read appState matches the written one
            assertEquals(appState.getUnits(), readAppState.getUnits());
            assertEquals(appState.getCurrentTown(), readAppState.getCurrentTown());
            assertEquals(appState.getHistory(), readAppState.getHistory());
            assertEquals(appState.getFavourites(), readAppState.getFavourites());

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());

        }
    }

    // Helper method to write data to a file
    private void writeFile(String filename, String data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(data);
        }
    }

    // Helper method to delete a file
    private void deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    // Helper method to read ApplicationStateManager object from file
    private ApplicationStateManager readAppStateFromFile(String filename) throws Exception {
        ReadAndWriteToFile fileHandler = new ReadAndWriteToFile();
        fileHandler.readFromFile(filename);
        return fileHandler.appState;
    }
}
