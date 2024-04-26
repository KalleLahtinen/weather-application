package fi.tuni.prog3.weatherapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
    ChatGPT 3.5 was mainly used in the creation of this class as help with 
    troubleshooting and generating Javadoc comments.
*/

/**
 * This class manages measurement units based on different measurement systems (metric or imperial).
 * It provides functionality to change the measurement system and get the corresponding units
 * for temperature, rainfall, and wind speed.
 * 
 * @author Kalle Lahtinen
 */
public final class MeasurementSystem {
    private String currentSystem;
    private final StringProperty tempUnit = new SimpleStringProperty();
    private final StringProperty rainUnit = new SimpleStringProperty();
    private final StringProperty windUnit = new SimpleStringProperty();

    /**
     * Constructs a new MeasurementSystem and initializes it to use the metric system.
     * 
     * @param units The units of measurement the object is initialized to.
     */
    public MeasurementSystem(String units) {
        changeSystem(units);
    }

    /**
     * Changes the measurement system to either metric or imperial 
     * and updates unit properties accordingly. 
     * 
     * @param newSystem The new measurement system to set. Valid values are "metric" or "imperial".
     * @return true if the system was successfully changed, false if the input was not valid.
     */
    public boolean changeSystem(String newSystem) {
        if (!newSystem.equals("metric") && !newSystem.equals("imperial")) {
            return false;
        }
        currentSystem = newSystem;
        
        switch(newSystem) {
            case "metric" -> {
                tempUnit.set("°C");
                rainUnit.set("mm");
                windUnit.set("m/s");
            }
            case "imperial" -> {
                tempUnit.set("°F");
                // OpenWeatherMap only supports mm for rain amount
                rainUnit.set("mm");
                windUnit.set("mph");
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the current measurement system.
     * 
     * @return The current measurement system (metric or imperial).
     */
    public String getCurrentSystem() {
        return currentSystem;
    }

    /**
     * Returns the property for temperature unit.
     * 
     * @return A {@link StringProperty} representing the temperature unit.
     */
    public StringProperty tempUnitProperty() {
        return tempUnit;
    }

    /**
     * Returns the property for rainfall unit.
     * 
     * @return A {@link StringProperty} representing the rainfall unit.
     */
    public StringProperty rainUnitProperty() {
        return rainUnit;
    }

    /**
     * Returns the property for wind speed unit.
     * 
     * @return A {@link StringProperty} representing the wind speed unit.
     */
    public StringProperty windUnitProperty() {
        return windUnit;
    } 
}