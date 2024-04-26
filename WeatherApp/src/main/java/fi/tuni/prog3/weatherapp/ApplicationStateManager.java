package fi.tuni.prog3.weatherapp;

import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/*
    ChatGPT 3.5 was heavily utilized in this class to brainstorm possible 
    courses of action, best practises, JavaFX features and then iterating 
    code to make work more efficient. This made it possible to focus on 
    the bigger picture and architecture, with cleaner class division and code.
 */

/**
 * Manages the application state including measurement units, current city, 
 * and lists of favorite cities and city search history.
 * 
 * @author Roope Kärkkäinen
 */
public class ApplicationStateManager {
    /** The measurement units used in the application. */
    public String units;
    /** The current city selected in the application. */
    public StringProperty currentCity;
    /** The list of cities in the search history. */
    public ListProperty<String> history;
    /** The list of favorite cities marked by the user. */
    public ListProperty<String> favourites;

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
        this.currentCity = new SimpleStringProperty(currentTown);
        this.history = new SimpleListProperty<>(FXCollections.observableArrayList(history));
        this.favourites = new SimpleListProperty<>(FXCollections.observableArrayList(favourites));
    }
    
    /**
     * Constructs an ApplicationStateManager with default values.
     * Default units are "metric", default city is "Helsinki", and both history
     * and favorite cities lists are initialized as empty lists.
     */
    public ApplicationStateManager() {
        this.units = "metric";
        this.currentCity = new SimpleStringProperty("Helsinki");
        this.history = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.favourites = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    
    /**
     * Adds a city to the list of favorite cities.
     * Checks if the city already exists in the list and if the list has 
     * reached its maximum capacity.
     * 
     * @param city The city to add to favorites.
     * @return true if the city is added successfully, false otherwise.
     */
    public boolean addFavoriteCity(String city) {
        if (favourites.size() >= 8) {
            favourites.remove(favourites.get(0));
        }
       
        // Move city to start of list if already on it
        if (favourites.contains(city)) {
            removeFavoriteCity(city);
            favourites.add(city);
        } else {
            favourites.add(city);
        }
        return true;
    }

    /**
     * Removes a city from the list of favorite cities.
     * 
     * @param city The city to remove from favorites.
     * @return true if the city is removed successfully, false if the city 
     * doesn't exist in favorites.
     */
    public boolean removeFavoriteCity(String city) {
        if (favourites.contains(city)) {
            favourites.remove(city);
            return true;
        }
        return false;
    }
    
    /**
     * Returns a Boolean indicating if current city is in favorites.
     * 
     * @return a Boolean indicating if current city is in favorites.
     */
    public boolean isCurrentCityFavourited() {
        return favourites.contains(this.getCurrentCity());
    }
    
    /**
     * Method to add a city to the search history.
     * 
     * @param city The city to add to history.
     */
    public void addCityToHistory(String city) {
        if (history.contains(city)) {
            history.remove(city);
        }

        if (history.size() >= 8) {
            history.remove(history.get(0));
        }    
        history.add(city);
    }
    
    /**
     * Method to remove a city from the search history.
     * 
     * @param city The city to remove from history.
     * @return True if the city is removed successfully, false if it doesn't 
     * exist in history.
     */
    public boolean removeCityFromHistory(String city) {
        return history.remove(city);
    }
    
    /**
    * Provides the current measurement units.
    * 
    * @return the current measurement units used in the application, such as 
    * "metric" or "imperial".
    */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the measurement units.
     * 
     * @param units the measurement units to set, such as "metric" or 
     * "imperial".
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Retrieves the currently selected city.
     * 
     * @return the current city.
     */
    public String getCurrentCity() {
        return currentCity.get();
    }

    /**
     * Sets the currently selected city.
     * 
     * @param city the city to set as the current city.
     */
    public void setCurrentCity(String city) {
        this.currentCity.set(city);
    }
    
    /**
     * Retrieves the property for the current city.
     * 
     * @return the property for the current city string.
     */
    public StringProperty currentCityProperty() {
        return currentCity;
    }

    /**
     * Retrieves the property for the search history list.
     * 
     * @return the property for the list of last 10 searches made by user.
     */
    public ListProperty<String> historyProperty() {
        return history;
    }

    /**
     * Retrieves the property for the favourite cities list.
     * 
     * @return the property for the list of cities marked as favourite by user.
     */
    public ListProperty<String> favouritesProperty() {
        return favourites;
    }

    /**
     * Retrieves the list of favorite cities.
     * 
     * @return a list of cities marked as favorites by the user.
     */
    public List<String> getFavourites() {
        return favourites.get();
    }
}
