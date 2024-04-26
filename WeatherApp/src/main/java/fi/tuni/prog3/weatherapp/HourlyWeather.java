package fi.tuni.prog3.weatherapp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;

/*
    ChatGPT 3.5 was heavily utilized in the creation and documentation of this class 
    to brainstorm possible ways of implementation, refactor code and create Javadoc comments.
*/

/**
 * This class represents detailed hourly weather data, encapsulating various weather parameters.
 * Properties are used for all data fields to allow easy integration with JavaFX binding mechanisms,
 * enabling automatic UI updates when data changes.
 */
public class HourlyWeather {
    private final ObjectProperty<Instant> date = new SimpleObjectProperty<>();
    private final DoubleProperty temperature = new SimpleDoubleProperty();
    private final DoubleProperty feelsLike = new SimpleDoubleProperty();
    private final IntegerProperty pressure = new SimpleIntegerProperty();
    private final IntegerProperty humidity = new SimpleIntegerProperty();
    private final DoubleProperty windSpeed = new SimpleDoubleProperty();
    private final IntegerProperty windDirection = new SimpleIntegerProperty();
    private final StringProperty weatherDescription = new SimpleStringProperty();
    private final StringProperty weatherIcon = new SimpleStringProperty();
    private final DoubleProperty rain1h = new SimpleDoubleProperty();
    private final ObjectProperty<String> iconCode = new SimpleObjectProperty<>();
    private final StringProperty units = new SimpleStringProperty();

    /**
     * Constructs a new {@code HourlyWeather} instance.
     * 
     * @param date The date and time of the weather data
     * @param temperature The temperature in {@code units}.
     * @param feelsLike The apparent temperature in {@code units}.
     * @param pressure The atmospheric pressure in {@code units}.
     * @param humidity The relative humidity in percentage
     * @param windSpeed The wind speed in meters per second
     * @param windDirection The wind direction in degrees
     * @param weatherDescription A textual description of the weather
     * @param weatherIcon An identifier for an icon representing the weather conditions
     * @param rain1h The volume of rain in the last hour in {@code units}.
     * @param iconCode A textual representation of the associated weather icon.
     * @param units The units of measurement the data is represented in.
     *              (metric, imperial, standard ( = Kelvin in temperature))
     */
    public HourlyWeather(Instant date, double temperature, double feelsLike, int pressure, 
            int humidity, double windSpeed, int windDirection, 
            String weatherDescription, String weatherIcon, double rain1h, String iconCode, String units) {
        this.date.set(date);
        this.temperature.set(temperature);
        this.feelsLike.set(feelsLike);
        this.pressure.set(pressure);
        this.humidity.set(humidity);
        this.windSpeed.set(windSpeed);
        this.windDirection.set(windDirection);
        this.weatherDescription.set(weatherDescription);
        this.weatherIcon.set(weatherIcon);
        this.rain1h.set(rain1h);
        this.iconCode.set(iconCode);
        this.units.set(units);
    }

    /**
     * Returns the date property.
     * @return the property for the date and time of the weather data.
     */
    public ObjectProperty<Instant> dateProperty() {
        return date;
    }

    /**
     * Returns the temperature property.
     * @return the property for the temperature in degrees Celsius.
     */
    public DoubleProperty temperatureProperty() {
        return temperature;
    }

    /**
     * Returns the feels-like temperature property.
     * @return the property for the apparent temperature in degrees Celsius.
     */
    public DoubleProperty feelsLikeProperty() {
        return feelsLike;
    }

    /**
     * Returns the pressure property.
     * @return the property for the atmospheric pressure in hPa.
     */
    public IntegerProperty pressureProperty() {
        return pressure;
    }

    /**
     * Returns the humidity property.
     * @return the property for the relative humidity in percentage.
     */
    public IntegerProperty humidityProperty() {
        return humidity;
    }

    /**
     * Returns the wind speed property.
     * @return the property for the wind speed in meters per second.
     */
    public DoubleProperty windSpeedProperty() {
        return windSpeed;
    }

    /**
     * Returns the wind direction property.
     * @return the property for the wind direction in degrees.
     */
    public IntegerProperty windDirectionProperty() {
        return windDirection;
    }

    /**
     * Returns the weather description property.
     * @return the property for the textual description of the weather.
     */
    public StringProperty weatherDescriptionProperty() {
        return weatherDescription;
    }

    /**
     * Returns the weather icon property.
     * @return the property for the icon identifier representing the weather conditions.
     */
    public StringProperty weatherIconProperty() {
        return weatherIcon;
    }

    /**
     * Returns the rain1h property.
     * @return the property for the volume of rain in the last hour in mm.
     */
    public DoubleProperty rain1hProperty() {
        return rain1h;
    }
    
    /**
     * Returns the property for the iconCode string.
     * @return the property for the iconCode string
     */
    public ObjectProperty<String> iconCodeProperty() {
        return iconCode;
    }

    /**
     * Returns the units property.
     * @return the property for the units of measurement used by this object.
     */
    public StringProperty unitsProperty() {
        return units;
    }

    /**
     * Calculates the hour of the day from the date, adjusted to the specified timezone.
     * 
     * @param zoneId the timezone ID to adjust the hour to (e.g., ZoneId.systemDefault())
     * @return the hour of the day.
     */
    public int getHour(ZoneId zoneId) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.get(), zoneId);
        return localDateTime.getHour();
    }
}