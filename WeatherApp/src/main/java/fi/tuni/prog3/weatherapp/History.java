package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the history of cities visited in the WeatherApp.
 * Implements the iReadAndWriteToFile interface for reading and writing 
 * from/to a file.
 * @author Roope Kärkkäinen
 */
public class History implements iReadAndWriteToFile {
    private List<City> history;
    
    /**
     * Constructor to initialize the History object.
     * Initializes the history list with an initial capacity of 10.
     */
    public History() {
        history = new ArrayList<>(10);
    }
    
    /**
     * Returns the list of cities in the history.
     * @return The list of cities in the history.
     */
    public List<City> getHistory() {
        return history;
    }

    /**
     * Adds a city to the history of visited cities.
     * Checks if the city already exists in the history and if the history has reached its maximum capacity.
     * If the history is full, removes the oldest entry before adding the new city.
     * @param city The city to add to history.
     * @return true if the city is added successfully, false otherwise.
     */
    public boolean addCityToHistory(City city) {
        if (history.contains(city)) {
            return false;
        }

        if (history.size() == 10) {
            // Remove oldest entry
            history.remove(history.get(0));
            history.add(city);
            return true;
        }

        history.add(city);
        return true;
    }
    
    /**
     * Removes a city from the history of visited cities.
     * @param city The city to remove from history.
     * @return true if the city is removed successfully, false if the city 
     * doesn't exist in history.
     */
    public boolean removeCityFromHistory(City city) {
        if (history.contains(city)) {
            history.remove(city);
            return true;
        }
        
        return false;
    }
    
    /**
     * Reads city history from a file and populates the history list.
     * Overrides the method from the iReadAndWriteToFile interface.
     * @param fileName The name of the file to read from.
     * @throws Exception if there's an error reading from the file.
     */
    @Override
    public void readFromFile(String fileName) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                                     new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(new City(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading history from file: " 
                    + e.getMessage());
        }
    }
    
    /**
     * Writes city history to a file.
     * Overrides the method from the iReadAndWriteToFile interface.
     * @param fileName The name of the file to write to.
     * @return true if writing to file is successful, false otherwise.
     * @throws Exception if there's an error writing to the file.
     */
    @Override
    public boolean writeToFile(String fileName) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {
            for (int i = 0; i < history.size(); i++) {
                writer.write(history.get(i).getCity());
                writer.newLine();
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}
