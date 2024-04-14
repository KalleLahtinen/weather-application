package fi.tuni.prog3.weatherapp;

import java.time.Instant;
import java.util.Map;

/**
 * Interface for extracting data from the OpenWeatherMap API.
 */
public interface iAPI {

    /**
     * Returns coordinates for a location.
     * @param loc Name of the location for which coordinates should be fetched.
     * @return Coordinate record that contains latitude and longitude as double.
     */
    public Coordinate getCoordinates(String loc);

    /**
     * Returns a daily forecast for the given location.
     * @param loc Name of the location for which weather should be fetched.
     * @param units The units of measurement the data is to be represented in.
     * @return Forecast as a map of Instant and DailyWeather pairs.
     */
    public Map<Instant, DailyWeather> getDailyForecast(String loc, String units);
    
    /**
     * Returns a Hourly forecast for the given location.
     * @param loc Name of the location for which weather should be fetched.
     * @param units The units of measurement the data is to be represented in.
     * @return Forecast as a map of Instant and HourlyWeather pairs.
     */
    public Map<Instant, HourlyWeather> getHourlyForecast(String loc, String units);
}
