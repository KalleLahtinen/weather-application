package fi.tuni.prog3.weatherapp;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * Class for reading and writing favorite cities and history to/from JSON files.
 * 
 * @author Roope Kärkkäinen and Kalle Lahtinen
 */
public class ReadAndWriteToFile implements iReadAndWriteToFile {
    
    /**
     * Method to read application state from a JSON file.
     * 
     * @return A ApplicationStateManager object created based on read file, 
     *         a fresh instance if file could not be read.
     */
    @Override
    public ApplicationStateManager readFromFile() {
        Gson gson = new Gson();
        ApplicationStateManager appState = null;

        try (Reader reader = new FileReader("appstate.json")) {
            appState = gson.fromJson(reader, ApplicationStateManager.class);
        } catch (IOException e) {
            appState = new ApplicationStateManager();
        }
        
        if (appState == null) { 
            appState = new ApplicationStateManager();
        }
        return appState;
    }
    
    /**
     * Method to write application state to a JSON file.
     */
    @Override
    public void writeToFile(ApplicationStateManager appState) {
        Gson gson = new Gson(); // Create a Gson object for JSON serialization
        String json = gson.toJson(appState); // Convert ApplicationStateManager object to JSON string

        // Write JSON string to file
        try (FileWriter writer = new FileWriter("appstate.json")) {
            writer.write(json); // Write JSON string to file
        } catch (IOException e) { // Catch IOException if file writing fails
            String errorMessage = "Error writing to file: appstate.json";
            LoggingInformation.logError(errorMessage, e);
        }
    }
}
