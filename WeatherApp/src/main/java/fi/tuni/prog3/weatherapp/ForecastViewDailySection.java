package fi.tuni.prog3.weatherapp;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A class for creating the daily weather section of the forecast view with 
 * styling and values binded to corresponding data in forecastView.
 * 
 * @author Kalle Lahtinen
 */
public class ForecastViewDailySection {
    private HBox daysBox;
    
    public HBox getDaysBox() {
        return daysBox;
    }
    
    /**
     * Creates and returns a HBox containing the daily weather information.
     * This includes min and max temperature for the day.
     *
     * @return a HBox filled with Text nodes displaying daily weather data.
     */
    HBox createDailyWeatherSection(ForecastView forecastView) {
        daysBox = new HBox();
        daysBox.setAlignment(Pos.CENTER);
        daysBox.getStyleClass().add("days-box");
        forecastView.setupDailyWeatherBindings(); // Initialize bindings
        
        for (int i = 0; i < forecastView.displayedDays.size(); i++) {
            ObjectProperty<DailyWeather> weatherProp = forecastView.displayedDays.get(i);
            VBox dayBox = new VBox(10);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.getStyleClass().add("day-box");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM").
                    withLocale(Locale.getDefault());
            
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.textProperty().bind(Bindings.createStringBinding(() ->
                formatter.format(weatherProp.get().getDate().atZone(ZoneId.systemDefault())),
                weatherProp));
            
            Label tempRange = new Label();
            tempRange.getStyleClass().add("temp-range");
            tempRange.textProperty().bind(Bindings.format("%.0f\u00b0C ... %.0f\u00b0C",
                Bindings.selectDouble(weatherProp, "minTemp"),
                Bindings.selectDouble(weatherProp, "maxTemp")));
            
            dayBox.getChildren().addAll(dateLabel, tempRange);
            daysBox.getChildren().add(dayBox);

            final int index = i;
            dayBox.setOnMouseClicked(event -> {
                forecastView.currentDayIndex.set(index);
            });
        }

        // Initialize the selected day visual
        if (!daysBox.getChildren().isEmpty()) {
            forecastView.currentDayIndex.set(0); // Set to first day by default
        }

        return daysBox;
    }
}
