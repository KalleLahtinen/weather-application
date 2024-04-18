package fi.tuni.prog3.weatherapp;

/**
 * Represents a city in the WeatherApp.
 * Provides methods to get the name of the city.
 * This class is a simple data class with only one attribute representing 
 * the name of the city.
 * 
 * @author Roope Kärkkäinen
 */
public class City {
    private String city;
    
    /**
     * Constructor to create a City object with the specified city name.
     * @param city The name of the city.
     */
    public City(String city) {
        this.city = city;
    }
    
    /**
     * Returns the name of the city.
     * @return The name of the city.
     */
    public String getCity() {
        return this.city;
    }
}
