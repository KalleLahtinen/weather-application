package fi.tuni.prog3.weatherapp;

import java.util.List;

/**
 * Interface for extracting data from the OpenWeatherMap API.
 */
public interface iAPI {

    /**
     * Returns coordinates for a location.
     * @param loc Name of the location for which coordinates should be fetched.
     * @return double.
     */
    public List<Double> lookUpLocation(String loc);

    /**
     * Returns the current weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return String.
     */
    public Weather getCurrentWeather(double lat, double lon);

    /**
     * Returns a forecast for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return String.
     */
    public List<Weather> getForecast(double lat, double lon);
}
