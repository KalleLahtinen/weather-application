package fi.tuni.prog3.weatherapp;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
    ChatGPT 3.5 was heavily utilized in this class to brainstorm possible 
    courses of action, best practises, javaFX features and then iterating 
    code to make work more efficient. This made it possible to focus on 
    the bigger picture and architecture, with cleaner class division and code.
 */

/**
 * Represents the hourly section of a forecast view, displaying weather information 
 * for each hour within a specified period.
 * 
 * @author Kalle Lahtinen
 */
public final class ForecastViewHourlySection {
    private final ObservableList<VBox> hourlyBoxes = FXCollections.observableArrayList();
    private ScrollPane scrollPane;
    private final ForecastViewController fv;
    private final Integer hourWidth = 35;

    /**
     * Constructs a new ForecastViewHourlySection associated with the given ForecastViewController.
     *
     * @param forecastView the root forecast view the section is part of.
     */
    public ForecastViewHourlySection(ForecastViewController forecastView) {
        this.fv = forecastView;
    }

    /**
     * Creates and returns a scrollable view containing hourly weather data.
     * 
     * @return a ScrollPane containing the HBox with hourly weather views.
     */
    public ScrollPane createHourlyWeatherSection() {
        HBox hoursBox = new HBox(5);
        hoursBox.setAlignment(Pos.CENTER);

        scrollPane = new ScrollPane(hoursBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(false);
        // Ensure that the HBox can extend beyond the width of the ScrollPane
        scrollPane.setFitToWidth(false);
        
        // Make space for the scrollbar
        scrollPane.prefHeightProperty().bind(hoursBox.heightProperty().add(20));
        
        // Bind hoursBox children to hourlyBoxes list
        Bindings.bindContent(hoursBox.getChildren(), hourlyBoxes);

        return scrollPane; 
    }

    /**
     * Sets up bindings for the hourly weather data within a specific day range.
     *
     * @param selectedDayStart the start instant of the day for which to display weather.
     */
    public void setupHourlyWeatherBindings(Instant selectedDayStart) {
        Instant endDay = selectedDayStart.plus(5, ChronoUnit.DAYS);
        hourlyBoxes.clear();

        fv.hourlyWeathers.entrySet().stream()
            .filter(entry -> !entry.getKey().isBefore(selectedDayStart) && entry.getKey().isBefore(endDay))
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> hourlyBoxes.add(createHourBox(entry.getKey(), new SimpleObjectProperty<>(entry.getValue()))));
    }

    /**
     * Sets up bindings for the hourly weather data within a specific day range.
     *
     * @param selectedDayStart the start instant of the day for which to display weather.
     */
    private VBox createHourBox(Instant hour, ObjectProperty<HourlyWeather> weatherProp) {
        VBox hourBox = new VBox(6);
        hourBox.setPrefWidth(hourWidth);
        hourBox.setAlignment(Pos.CENTER);

        Label hourLabel = new Label(DateTimeFormatter.ofPattern("HH").format(hour.atZone(ZoneId.systemDefault())));
        hourLabel.getStyleClass().add("forecast-hour");
        
        Text hourlyWeatherIcon = new Text();
        hourlyWeatherIcon.setFont(new Font("Weather Icons", 30));
        hourlyWeatherIcon.textProperty().bind(Bindings.format("%s",
                Bindings.selectString(weatherProp, "iconCode")));
        hourlyWeatherIcon.setId("hourlyWeatherIcon");

        Label tempLabel = new Label();
        tempLabel.textProperty().bind(Bindings.format("%.0f", Bindings.selectDouble(weatherProp, "temperature")));
        tempLabel.getStyleClass().add("forecast-basic");
        
        Label windLabel = new Label();
        windLabel.textProperty().bind(Bindings.format("%.0f", Bindings.selectDouble(weatherProp, "windSpeed")));
        windLabel.getStyleClass().add("forecast-basic");

        Label rainLabel = new Label();
        rainLabel.textProperty().bind(Bindings.format("%.1f", Bindings.selectDouble(weatherProp, "rain1h")));
        rainLabel.getStyleClass().add("forecast-basic");

        Label humidityLabel = new Label();
        humidityLabel.textProperty().bind(Bindings.format("%d%%", Bindings.selectInteger(weatherProp, "humidity")));
        humidityLabel.getStyleClass().add("forecast-basic");

        hourBox.getChildren().addAll(hourLabel, hourlyWeatherIcon, tempLabel, windLabel, rainLabel, humidityLabel);
        return hourBox;
    }
    
    public void scrollToHour() {
        Integer currentDayIndex = fv.currentDayIndex.get();
        
        Instant earliestHour = fv.hourlyWeathers.get().keySet().stream()
                             .min(Instant::compareTo)
                             .orElse(null);
        
        if (currentDayIndex == 0) {
            scrollPane.setHvalue(0);
            
        } else if (currentDayIndex < 4) {
            // Convert the instant to a ZonedDateTime
            ZonedDateTime zonedDateTime = earliestHour.atZone(ZoneId.of("Europe/Helsinki"));

            // Retrieve the hour as an integer between 0 and 23
            int hour = zonedDateTime.getHour();
            int timeToNextDay = 24 - hour;
            double secondDayStart = 96.0 / (96 - timeToNextDay) - 1;
            
            double scrollTo = secondDayStart + (0.25 * (currentDayIndex - 1));
            scrollPane.setHvalue(scrollTo);
            
            System.out.println("timeToNextDay: " + timeToNextDay);
            System.out.println("Scroll to proportion: " + scrollTo);
            
        } else {
            scrollPane.setHvalue(1);
        }
    }
}
