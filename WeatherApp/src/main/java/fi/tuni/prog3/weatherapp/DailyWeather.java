package fi.tuni.prog3.weatherapp;

import java.time.Instant;

/**
 * Represents daily weather data including min and max temperature and other weather conditions.
 */
public class DailyWeather {
    private final Instant date;
    private final double dayTemp;
    private final double minTemp;
    private final double maxTemp;
    private final double dayFeelsLike;
    private final double windSpeed;
    private final double rainVolume;
    private final String description;
    private final String units;

    /**
     * Constructs a new {@code DailyWeather} instance.
     * 
     * @param date The specific date of the weather data, not null.
     * @param dayTemp The average temperature for the day in {@code units}.
     * @param minTemp The minimum recorded temperature for the day in {@code units}.
     * @param maxTemp The maximum recorded temperature for the day in {@code units}.
     * @param dayFeelsLike The feels-like temperature for the day in {@code units}.
     * @param windSpeed The wind speed in {@code units}.
     * @param rainVolume The volume of rainfall in {@code units}.
     * @param description A textual description of the weather conditions.
     * @param units The units of measurement the data is represented in.
     */
    public DailyWeather(Instant date, double dayTemp, double minTemp, 
            double maxTemp, double dayFeelsLike, double windSpeed, 
            double rainVolume, String description, String units) {
        this.date = date;
        this.dayTemp = dayTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.dayFeelsLike = dayFeelsLike;
        this.windSpeed = windSpeed;
        this.rainVolume = rainVolume;
        this.description = description;
        this.units = units;
    }

    /**
     * Returns the date of the weather data.
     * @return the date as an {@code Instant}.
     */
    public Instant getDate() {
        return date;
    }

    /**
     * Returns the average day temperature.
     * @return the day temperature in {@code units}.
     */
    public double getDayTemp() {
        return dayTemp;
    }

    /**
     * Returns the minimum temperature of the day.
     * @return the minimum temperature in {@code units}.
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * Returns the maximum temperature of the day.
     * @return the maximum temperature in {@code units}.
     */
    public double getMaxTemp() {
        return maxTemp;
    }

    /**
     * Returns the feels-like temperature for the day.
     * @return the feels-like temperature in {@code units}.
     */
    public double getDayFeelsLike() {
        return dayFeelsLike;
    }

    /**
     * Returns the wind speed.
     * @return the wind speed in {@code units}.
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Returns the volume of rain for the day.
     * @return the rain volume in {@code units}.
     */
    public double getRainVolume() {
        return rainVolume;
    }

    /**
     * Returns a description of the weather conditions.
     * @return the textual weather description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the units of measurement this object uses.
     * @return the units of measurement this object uses.
     */
    public String getUnits() {
        return units;
    }
}
