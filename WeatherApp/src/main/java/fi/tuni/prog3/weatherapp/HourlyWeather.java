package fi.tuni.prog3.weatherapp;

import java.time.Instant;

/**
 * Represents hourly weather data including temperature, wind speed, humidity, and other weather conditions.
 */
public class HourlyWeather {
    private final Instant date;
    private final double temperature;
    private final double feelsLike;
    private final int pressure;
    private final int humidity;
    private final double windSpeed;
    private final int windDirection;
    private final String weatherDescription;
    private final String weatherIcon;
    private final double rain1h;
    private final String units;

    /**
     * Constructs an instance of HourlyWeather with specified weather parameters.
     * 
     * @param date the date and time of the weather data
     * @param temperature the temperature in degrees Celsius
     * @param feelsLike the apparent temperature in degrees Celsius
     * @param pressure the atmospheric pressure in hPa
     * @param humidity the relative humidity in percentage
     * @param windSpeed the wind speed in meters per second
     * @param windDirection the wind direction in degrees
     * @param weatherDescription a textual description of the weather
     * @param weatherIcon an identifier for an icon representing the weather conditions
     * @param rain1h the volume of rain in the last hour in mm
     * @param units The units of measurement the data is represented in.
     */
    public HourlyWeather(Instant date, double temperature, double feelsLike, int pressure, 
            int humidity, double windSpeed, int windDirection, 
            String weatherDescription, String weatherIcon, double rain1h, String units) {
        this.date = date;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.rain1h = rain1h;
        this.units = units;
    }
    
    /**
     * Returns the date and time of the weather data.
     * @return the date as an {@code Instant}.
     */
    public Instant getDate() {
        return date;
    }

    /**
     * Returns the temperature.
     * @return the temperature in degrees Celsius.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Returns the feels-like temperature.
     * @return the feels-like temperature in degrees Celsius.
     */
    public double getFeelsLike() {
        return feelsLike;
    }

    /**
     * Returns the atmospheric pressure.
     * @return the pressure in hPa.
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * Returns the relative humidity.
     * @return the humidity in percentage.
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Returns the wind speed.
     * @return the wind speed in meters per second.
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Returns the wind direction.
     * @return the wind direction in degrees.
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * Returns a textual description of the current weather.
     * @return the weather description.
     */
    public String getWeatherDescription() {
        return weatherDescription;
    }

    /**
     * Returns the icon identifier for the current weather conditions.
     * @return the weather icon identifier.
     */
    public String getWeatherIcon() {
        return weatherIcon;
    }

    /**
     * Returns the amount of rainfall in the last hour.
     * @return the rain volume in millimeters.
     */
    public double getRain1h() {
        return rain1h;
    }
    
    /**
     * Returns the units of measurement this object uses.
     * @return the units of measurement this object uses.
     */
    public String getUnits() {
        return units;
    }
}