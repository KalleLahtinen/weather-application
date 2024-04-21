/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Kalle Lahtinen
 */
public class ForecastViewDailySection {

    /**
     * Creates and returns a HBox containing the daily weather information.
     * This includes min and max temperature for the day.
     *
     * @return a HBox filled with Text nodes displaying daily weather data.
     */
    HBox createDailyWeatherSection(ForecastView forecastView) {
        HBox daysBox = new HBox();
        daysBox.setAlignment(Pos.CENTER);
        daysBox.getStyleClass().add("days-box");
        forecastView.setupDailyWeatherBindings(); // Initialize bindings
        
        for (ObjectProperty<DailyWeather> weatherProp : forecastView.displayedDays) {
            VBox dayBox = new VBox(10);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.getStyleClass().add("day-box");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM").
                    withLocale(Locale.getDefault());
            
            // Bind and display date and temperature range
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.textProperty().
                    bind(Bindings.createStringBinding(() -> formatter.format(weatherProp.get().
                    getDate().atZone(ZoneId.systemDefault())), weatherProp));
            
            Label tempRange = new Label();
            tempRange.getStyleClass().add("temp-range");
            tempRange.textProperty().
                    bind(Bindings.format("%.0f\u00b0C ... %.0f\u00b0C",
                    Bindings.selectDouble(weatherProp, "minTemp"),
                    Bindings.selectDouble(weatherProp, "maxTemp")));
            
            dayBox.getChildren().addAll(dateLabel, tempRange);
            daysBox.getChildren().add(dayBox);
            
            // Click event for selecting a day
            dayBox.setOnMouseClicked(event -> {
                if (forecastView.selectedDay.get() != null) {
                    forecastView.selectedDay.get().getStyleClass().
                            remove("selected-day");
                }
                dayBox.getStyleClass().add("selected-day");
                forecastView.selectedDay.set(dayBox);
            });
        }
        // Select the first day by default if it exists
        if (!daysBox.getChildren().isEmpty()) {
            VBox firstDayBox = (VBox) daysBox.getChildren().get(0);
            firstDayBox.getStyleClass().add("selected-day");
            forecastView.selectedDay.set(firstDayBox);
        }
        return daysBox;
    }
    
}
