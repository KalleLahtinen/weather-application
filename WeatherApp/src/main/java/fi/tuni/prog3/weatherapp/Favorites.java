package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roope Kärkkäinen
 */
public class Favorites implements iReadAndWriteToFile {
    private List<City> favoriteCities;
    
    public Favorites() {
        favoriteCities = new ArrayList<>();
    }
    
    public void addFavoriteCity(City city) {
        favoriteCities.add(city);
    }
    
    public void removeFavoriteCity(City city) {
        favoriteCities.remove(city);
    }
    
    public List<City> getFavoriteCities() {
        return favoriteCities;
    }
    
    @Override
    public readFromFile(String fileName) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                                     new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                favoriteCities.add(new City(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading favorites from file: " + e.getMessage());
        }
    }
}
