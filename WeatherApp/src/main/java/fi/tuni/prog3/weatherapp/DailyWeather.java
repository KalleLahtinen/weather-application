package fi.tuni.prog3.weatherapp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.Instant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class represents detailed daily weather data, encapsulating various weather parameters.
 * Properties are used for all data fields to allow easy integration with JavaFX binding mechanisms,
 * enabling automatic UI updates when data changes.
 */
public class DailyWeather {
    private final Instant date;
    private final DoubleProperty dayTemp = new SimpleDoubleProperty();
    private final DoubleProperty minTemp = new SimpleDoubleProperty();
    private final DoubleProperty maxTemp = new SimpleDoubleProperty();
    private final DoubleProperty dayFeelsLike = new SimpleDoubleProperty();
    private final DoubleProperty windSpeed = new SimpleDoubleProperty();
    private final DoubleProperty rainVolume = new SimpleDoubleProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<String> iconCode = new SimpleObjectProperty<>();
    private final String units;
    
    /**
     * Constructs a new {@code DailyWeather} instance.
     * 
     * @param date The date and time of the weather data
     * @param dayTemp The average temperature for the day in {@code units}.
     * @param minTemp The minimum recorded temperature for the day in {@code units}.
     * @param maxTemp The maximum recorded temperature for the day in {@code units}.
     * @param dayFeelsLike The feels-like temperature for the day in {@code units}.
     * @param windSpeed The wind speed in {@code units}.
     * @param rainVolume The volume of rainfall in {@code units}.
     * @param description A textual description of the weather conditions.
     * @param iconCode A textual representation of the associated weather icon.
     * @param units The units of measurement the data is represented in.
     *              (metric, imperial, standard ( = Kelvin in temperature))
     */
    public DailyWeather(Instant date, double dayTemp, double minTemp, double maxTemp, 
                        double dayFeelsLike, double windSpeed, double rainVolume, 
                        String description, String iconCode, String units) {
        this.date = date;
        this.dayTemp.set(dayTemp);
        this.minTemp.set(minTemp);
        this.maxTemp.set(maxTemp);
        this.dayFeelsLike.set(dayFeelsLike);
        this.windSpeed.set(windSpeed);
        this.rainVolume.set(rainVolume);
        this.description.set(description);
        this.iconCode.set(iconCode);
        this.units = units;
    }

    /**
     * Returns the date of this DailyWeather object.
     * @return the date as an Instant
     */
    public Instant getDate() {
        return date;
    }

    /**
     * Returns the property for the average day temperature.
     * @return the day temperature property
     */
    public DoubleProperty dayTempProperty() {
        return dayTemp;
    }

    /**
     * Returns the property for the minimum temperature.
     * @return the minimum temperature property
     */
    public DoubleProperty minTempProperty() {
        return minTemp;
    }

    /**
     * Returns the property for the maximum temperature.
     * @return the maximum temperature property
     */
    public DoubleProperty maxTempProperty() {
        return maxTemp;
    }

    /**
     * Returns the property for the feels-like temperature.
     * @return the day feels-like temperature property
     */
    public DoubleProperty dayFeelsLikeProperty() {
        return dayFeelsLike;
    }

    /**
     * Returns the property for the wind speed.
     * @return the wind speed property
     */
    public DoubleProperty windSpeedProperty() {
        return windSpeed;
    }

    /**
     * Returns the property for the rain volume.
     * @return the rain volume property
     */
    public DoubleProperty rainVolumeProperty() {
        return rainVolume;
    }

    /**
     * Returns the property for the weather description.
     * @return the description property
     */
    public StringProperty descriptionProperty() {
        return description;
    }
    
    /**
     * Returns the property for the iconCode string.
     * @return the property for the iconCode string
     */
    public ObjectProperty<String> iconCodeProperty() {
        return iconCode;
    }

    /**
     * Returns the units of measurement this object uses.
     * @return the units of measurement this object uses
     */
    public String getUnits() {
        return units;
    }
}