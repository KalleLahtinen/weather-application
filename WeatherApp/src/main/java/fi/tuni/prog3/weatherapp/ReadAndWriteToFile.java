package fi.tuni.prog3.weatherapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for reading and writing favorite cities and history to/from JSON files.
 * 
 * @author Roope Kärkkäinen
 */
public class ReadAndWriteToFile implements iReadAndWriteToFile {
    // Gson instance for JSON serialization and deserialization
    private Gson gson = new Gson();
    // List to hold favorite cities
    private List<String> favoriteCities;
    // List to hold history of visited cities
    private List<String> history;
    
    /**
     * Method to retrieve the list of favorite cities.
     * @return The list of favorite cities, or null if empty.
     */
    public List<String> getFavorites() {
        if (favoriteCities.size() == 0) {
            return null;
        }
        return favoriteCities;
    }
    
    /**
     * Method to retrieve the list of visited cities.
     * @return The list of visited cities, or null if empty.
     */
    public List<String> getHistory() {
        if (history.size() == 0) {
            return null;
        }
        return history;
    }

    /**
     * Method to add a city to the history of visited cities.
     * @param city The city to add to history.
     */
    public void addCityToHistory(String city) {
        if (history.contains(city)) {
            history.remove(city);
        }

        if (history.size() == 10) {
            // Remove oldest entry if history is full
            history.remove(history.get(0));
        }
        
        // Add new city to history
        history.add(city);
    }
    
    /**
     * Method to remove a city from the history of visited cities.
     * @param city The city to remove from history.
     * @return True if the city is removed successfully, false if it doesn't exist in history.
     */
    public boolean removeCityFromHistory(String city) {
        return history.remove(city);
    }
    
    /**
     * Method to write both favorite cities and history to separate JSON files.
     * @throws Exception if an error occurs during file writing.
     */
    public void writeFavoritesAndHistoryToFile() throws Exception {
        // Write favorite cities to favorites.json
        writeToFile("favorites.json", favoriteCities);
        // Write history to history.json
        writeToFile("history.json", history);
    }
    
    @Override
    public void readFromFile(String fileName) throws Exception {
        try (FileReader fileReader = new FileReader(fileName)) {
            // Parse the JSON file
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            // Convert the parsed element to JsonObject
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (fileName.equals("favorites.json")) {
                // Get the JsonArray of favorite cities
                JsonArray favoritesArray = jsonObject.getAsJsonArray("favorites");

                if (favoriteCities == null) {
                    favoriteCities = new ArrayList<>();
                }/* else {
                    favoriteCities.clear();
                }*/

                // Iterate over the JsonArray and add each city to history list
                for (JsonElement element : favoritesArray) {
                    favoriteCities.add(element.getAsString());
                }
            } else {
                JsonArray historyArray = jsonObject.getAsJsonArray("history");
                
                // Initialize history list if null
                if (history == null) {
                    history = new ArrayList<>();
                } else {
                    // Clear the list if it's not null to avoid duplications
                    history.clear();
                }

                // Iterate over the JsonArray and add each city to history list
                for (JsonElement element : historyArray) {
                    history.add(element.getAsString());
                }
            }
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }
    
    /**
     * Method to write a list of strings to a JSON file.
     * @param filename The name of the file to write to.
     * @param data The list of strings to write.
     * @throws Exception if an error occurs during file writing.
     */
    @Override
    public void writeToFile(String filename, List<String> data) throws Exception {
        // Create a JsonObject to hold the data
        JsonObject jsonObject = new JsonObject();
        // Create a JsonArray to hold the list of cities
        JsonArray jsonArray = new JsonArray();
        
        // Add each city to the JsonArray
        for (String city : data) {
            jsonArray.add(city);
        }
        
        // Check the filename to determine which key to use in the JsonObject
        if (filename.equals("favorites.json")) {
            jsonObject.add("favorites", jsonArray);
        } else {
            jsonObject.add("history", jsonArray);
        }
        
        // Convert the JsonObject to JSON string
        String jsonData = gson.toJson(jsonObject);
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
