package fi.tuni.prog3.weatherapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for reading and writing favorite cities and history to/from JSON files.
 * 
 * @author Roope Kärkkäinen
 */
public class ReadAndWriteToFile implements iReadAndWriteToFile {
    public ApplicationStateManager appState;
    
    /**
     * Method to read application state from a JSON file.
     * @param filename The name of the file to read from.
     * @throws Exception if an error occurs during file reading.
     */
    @Override
    public void readFromFile(String fileName) throws Exception {
        Gson gson = new Gson();

        this.appState = null;

        try (Reader reader = new FileReader("appstate.json")) {
            appState = gson.fromJson(reader, ApplicationStateManager.class);
        } catch (IOException e) {
            appState = new ApplicationStateManager();
        }
        
        if (appState == null) { 
            appState = new ApplicationStateManager();
        }   
    }
    
    /**
     * Method to write application state to a JSON file.
     * @param filename The name of the file to write to.
     * @throws Exception if an error occurs during file writing.
     */
    @Override
    public void writeToFile(String filename) throws Exception {
        Gson gson = new Gson(); // Create a Gson object for JSON serialization
        String json = gson.toJson(this.appState); // Convert ApplicationStateManager object to JSON string

        // Write JSON string to file
        try (FileWriter writer = new FileWriter("/appstate.json")) { // Open a FileWriter to write to the file
            writer.write(json); // Write JSON string to file
        } catch (IOException e) { // Catch IOException if file writing fails
            e.printStackTrace(); // Print stack trace for the IOException
        }
    }
}
