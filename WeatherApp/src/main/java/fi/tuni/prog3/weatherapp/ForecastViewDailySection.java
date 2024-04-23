package fi.tuni.prog3.weatherapp;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
    ChatGPT 3.5 was heavily utilized in this class to brainstorm possible 
    courses of action, best practises, javaFX features and then iterating 
    code to make work more efficient. This made it possible to focus on 
    the bigger picture and architecture, with cleaner class division and code.
 */

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
    HBox createDailyWeatherSection(ForecastViewController forecastView) {
        daysBox = new HBox();
        daysBox.setAlignment(Pos.CENTER);
        daysBox.getStyleClass().add("days-box");
        forecastView.setupDailyWeatherBindings(); // Initialize bindings
        
        // Populate daysBox with individual days
        for (int i = 0; i < forecastView.displayedDays.size(); i++) {
            ObjectProperty<DailyWeather> weatherProp = forecastView.displayedDays.get(i);
            VBox dayBox = new VBox();
            dayBox.setAlignment(Pos.CENTER);
            dayBox.getStyleClass().add("day-box");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM").
                    withLocale(Locale.getDefault());
            
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.textProperty().bind(Bindings.createStringBinding(() ->
                formatter.format(weatherProp.get().getDate().atZone(ZoneId.systemDefault())),
                weatherProp));
            
            Text dailyWeatherIcon = new Text();
            dailyWeatherIcon.setFont(new Font("Weather Icons", 40));
            dailyWeatherIcon.textProperty().bind(Bindings.format("%s",
                    Bindings.selectString(weatherProp, "iconCode")));
            
            Label tempRange = new Label();
            tempRange.getStyleClass().add("temp-range");
            tempRange.textProperty().bind(Bindings.format("%.0f%s ... %.0f%s",
                Bindings.selectDouble(weatherProp, "minTemp"),
                Bindings.selectString(forecastView.measurementSystem, "tempUnit"),
                Bindings.selectDouble(weatherProp, "maxTemp"),
                Bindings.selectString(forecastView.measurementSystem, "tempUnit")));
            VBox.setMargin(tempRange, new Insets(7, 0, 0, 0)); // Top, Right, Bottom, Left

            
            dayBox.getChildren().addAll(dateLabel, dailyWeatherIcon, tempRange);
            daysBox.getChildren().add(dayBox);

            final int index = i;
            dayBox.setOnMouseClicked(event -> {
                forecastView.currentDayIndex.set(index);
                forecastView.hourlySection.scrollToSelectedHour();
            });
        }

        // Initialize the selected day visual
        if (!daysBox.getChildren().isEmpty()) {
            forecastView.currentDayIndex.set(0); // Set to first day by default
        }

        return daysBox;
    }
}
