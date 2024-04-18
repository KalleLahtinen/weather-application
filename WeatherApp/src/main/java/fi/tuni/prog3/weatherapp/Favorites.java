package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        favoriteCities = new ArrayList<>(10);
    }
    
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
    
    public void removeFavoriteCity(City city) {
        favoriteCities.remove(city);
    }
    
    public List<City> getFavoriteCities() {
        return favoriteCities;
    }
    
    @Override
    public void readFromFile(String fileName) throws Exception {
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
