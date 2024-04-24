package fi.tuni.prog3.weatherapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the application state including measurement units, current city, 
 * and lists of favorite cities and city search history.
 * 
 * @author Roope Kärkkäinen
 */
public class ApplicationStateManager {
    public String units;
    public String currentCity;
    public List<String> history;
    public List<String> favouriteCities;

    /**
     * Constructs an ApplicationStateManager with specified initial values.
     * 
     * @param units The initial measurement units.
     * @param currentTown The initial city to be set as the current city.
     * @param history The initial list of visited cities.
     * @param favourites The initial list of favorite cities.
     */
    public ApplicationStateManager(String units, String currentTown, List<String> history, List<String> favourites) {
        this.units = units;
        this.currentCity = currentTown;
        this.history = history;
        this.favouriteCities = favourites;
    }
    
    /**
     * Constructs an ApplicationStateManager with default values.
     * Default units are "metric", default city is "Helsinki", and both history
     * and favorite cities lists are initialized as empty lists.
     */
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
     * Method to add a city to the search history.
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
     * Method to remove a city from the search history.
     * @param city The city to remove from history.
     * @return True if the city is removed successfully, false if it doesn't exist in history.
     */
    public boolean removeCityFromHistory(String city) {
        return history.remove(city);
    }
    
    /**
    * Provides the current measurement units.
    * @return the current measurement units used in the application, such as "metric" or "imperial".
    */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the measurement units.
     * @param units the measurement units to set, such as "metric" or "imperial".
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Retrieves the currently selected city.
     * @return the current city.
     */
    public String getCurrentCity() {
        return currentCity;
    }

    /**
     * Sets the currently selected city.
     * @param currentCity the city to set as the current city.
     */
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    /**
     * Gets the search history list.
     * @return a list of cities representing the user's search history.
     */
    public List<String> getHistory() {
        return history;
    }

    /**
     * Sets the search history list.
     * @param history a list of cities to set as the search history.
     */
    public void setHistory(List<String> history) {
        this.history = history;
    }

    /**
     * Retrieves the list of favorite cities.
     * @return a list of cities marked as favorites by the user.
     */
    public List<String> getFavourites() {
        return favouriteCities;
    }

    /**
     * Sets the list of favorite cities.
     * @param favourites a list of cities to be marked as favorites.
     */
    public void setFavourites(List<String> favourites) {
        this.favouriteCities = favourites;
    }
}
