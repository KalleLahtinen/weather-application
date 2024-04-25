package fi.tuni.prog3.weatherapp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
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
        } catch (FileNotFoundException e) {
            appState = new ApplicationStateManager();
        } catch (IOException e) {
            LoggingInformation.logError("Error reading from file: " + "appstate.json", e);
            appState = new ApplicationStateManager();
        } catch (JsonSyntaxException e) {
            LoggingInformation.logError("Invalid JSON format in file: " + "appstate.json", e);
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
        Gson gson = new Gson();
        String json = gson.toJson(appState);

        try (FileWriter writer = new FileWriter("appstate.json")) {
            writer.write(json);
        } catch (IOException e) {
            LoggingInformation.logError("Error writing to file: " + "appstate.json", e);
        }
    }
}
