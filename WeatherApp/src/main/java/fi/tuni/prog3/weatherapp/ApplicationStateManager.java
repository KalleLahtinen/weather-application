package fi.tuni.prog3.weatherapp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Roope Kärkkäinen
 */
public class ApplicationStateManager {
    public String units;
    public String currentCity;
    public List<String> history;
    public List<String> favouriteCities;

    public ApplicationStateManager(String units, String currentTown, List<String> history, List<String> favourites) {
        this.units = units;
        this.currentCity = currentTown;
        this.history = history;
        this.favouriteCities = favourites;
    }
    
    public ApplicationStateManager() {
        this.units = "metric";
        this.currentCity = "Helsinki";
        this.history = new ArrayList<>();
        this.favouriteCities = new ArrayList<>();
    }
    
    /**
     * Adds a city to the list of favorite cities.
     * Checks if the city already exists in the list and if the list has 
     * reached its maximum capacity.
     * @param city The city to add to favorites.
     * @return true if the city is added successfully, false otherwise.
     */
    public boolean addFavoriteCity(String city) {
       // TODO: If size 10 etc.
       if (favouriteCities.size() == 10) {
           favouriteCities.remove(favouriteCities.get(0));
       }
       
        if (favouriteCities.contains(city)) {
            removeFavoriteCity(city);
            favouriteCities.add(city);
        }
        return true;
    }

    /**
     * Removes a city from the list of favorite cities.
     * @param city The city to remove from favorites.
     * @return true if the city is removed successfully, false if the city 
     * doesn't exist in favorites.
     */
    public boolean removeFavoriteCity(String city) {
        if (favouriteCities.contains(city)) {
            favouriteCities.remove(city);
            return true;
        }
        return false;
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

    
    // Getters and setters for each field
    public String getUnits() {
        return units;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public List<String> getFavourites() {
        return favouriteCities;
    }

    public void setFavourites(List<String> favourites) {
        this.favouriteCities = favourites;
    }
    
}
