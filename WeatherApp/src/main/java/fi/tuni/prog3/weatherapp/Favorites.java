package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a list of favorite cities in the WeatherApp.
 * Implements the iReadAndWriteToFile interface for reading and writing 
 * from/to a file.
 * 
 * @author Roope Kärkkäinen
 */
public class Favorites implements iReadAndWriteToFile {
    private List<City> favoriteCities; // List to store favorite cities
    
    /**
     * Constructor to initialize the Favorites object.
     * Initializes the favoriteCities list with an initial capacity of 10.
     */
    public Favorites() {
        favoriteCities = new ArrayList<>(10);
    }
    
    /**
     * Adds a city to the list of favorite cities.
     * Checks if the city already exists in the list and if the list has 
     * reached its maximum capacity.
     * @param city The city to add to favorites.
     * @return true if the city is added successfully, false otherwise.
     */
    public boolean addFavoriteCity(City city) {
        if (favoriteCities.contains(city)) {
            return false;
        }
        
        if (favoriteCities.size() == 10) {
            return false;
        }

        favoriteCities.add(city);
        return true;
    }
    
    /**
     * Removes a city from the list of favorite cities.
     * @param city The city to remove from favorites.
     * @return true if the city is removed successfully, false if the city 
     * doesn't exist in favorites.
     */
    public boolean removeFavoriteCity(City city) {
        if (favoriteCities.contains(city)) {
            favoriteCities.remove(city);
            return true;
        }
        return false;
    }
    
    /**
     * Returns the list of favorite cities.
     * @return The list of favorite cities.
     */
    public List<City> getFavoriteCities() {
        return favoriteCities;
    }
    
    /**
     * Reads favorite cities from a file and populates the favoriteCities list.
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
                favoriteCities.add(new City(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading favorites from file: " 
                    + e.getMessage());
        }
    }
    
    /**
     * Writes favorite cities to a file.
     * Overrides the method from the iReadAndWriteToFile interface.
     * @param fileName The name of the file to write to.
     * @return true if writing to file is successful, false otherwise.
     * @throws Exception if there's an error writing to the file.
     */
    @Override
    public boolean writeToFile(String fileName) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < favoriteCities.size(); i++) {
                writer.write(favoriteCities.get(i).getCity());
                writer.newLine();
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}
