package fi.tuni.prog3.weatherapp;

import javafx.beans.property.MapProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

/**
 * Represents the hourly section of a forecast view, displaying weather information 
 * for each hour within a specified period.
 * @author Kalle Lahtinen
 */
public class ForecastViewHourlySection {
    private final ForecastView root;
    private ObservableList<VBox> hourlyBoxes = FXCollections.observableArrayList();
    private final MapProperty<Instant, HourlyWeather> hourlyWeathers =
            new SimpleMapProperty<>(FXCollections.observableHashMap());

    /**
     * Constructs a new ForecastViewHourlySection associated with the given ForecastView.
     *
     * @param fv the root ForecastView associated with this section.
     * @param newHourlyWeathers the initial set of hourly weather data to be displayed.
     */
    public ForecastViewHourlySection(ForecastView fv, Map<Instant, HourlyWeather> newHourlyWeathers) {
        this.root = fv;
    }

    /**
     * Creates and returns a scrollable view containing hourly weather data.
     * 
     * @return a ScrollPane containing the HBox with hourly weather views.
     */
    public ScrollPane createHourlyWeatherSection() {
        HBox hoursBox = new HBox(5);
        hoursBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(hoursBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        // Ensure that the HBox can extend beyond the width of the ScrollPane
        scrollPane.setFitToWidth(false);

        // Bind hoursBox children to hourlyBoxes list
        Bindings.bindContent(hoursBox.getChildren(), hourlyBoxes);

        return scrollPane; 
    }

    /**
     * Sets up bindings for the hourly weather data within a specific day range.
     *
     * @param selectedDayStart the start instant of the day for which to display weather.
     */
    private void setupHourlyWeatherBindings(Instant selectedDayStart) {
        Instant endDay = selectedDayStart.plus(5, ChronoUnit.DAYS);
        hourlyBoxes.clear();

        hourlyWeathers.entrySet().stream()
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
        VBox hourBox = new VBox(4);
        hourBox.setAlignment(Pos.CENTER);

        Label hourLabel = new Label(DateTimeFormatter.ofPattern("HH").format(hour.atZone(ZoneId.systemDefault())));
        Label tempLabel = new Label();
        tempLabel.textProperty().bind(Bindings.format("%.1f°C", Bindings.selectDouble(weatherProp, "temperature")));
        Label windLabel = new Label();
        windLabel.textProperty().bind(Bindings.format("%.1f m/s", Bindings.selectDouble(weatherProp, "windSpeed")));
        Label rainLabel = new Label();
        rainLabel.textProperty().bind(Bindings.format("%.1f mm", Bindings.selectDouble(weatherProp, "rain1h")));
        Label humidityLabel = new Label();
        humidityLabel.textProperty().bind(Bindings.format("%d%%", Bindings.selectInteger(weatherProp, "humidity")));

        hourBox.getChildren().addAll(hourLabel, tempLabel, windLabel, rainLabel, humidityLabel);
        return hourBox;
    }

    /**
     * Updates the hourly weather data with the given map.
     *
     * @param newHourlyWeathers the new map of hourly weather data.
     */
    public void updateHourlyWeathers(Map<Instant, HourlyWeather> newHourlyWeathers) {
        hourlyWeathers.clear();
        hourlyWeathers.putAll(newHourlyWeathers);
        Instant earliestHour = hourlyWeathers.get().keySet().stream()
                             .min(Instant::compareTo)
                             .orElse(null);
        setupHourlyWeatherBindings(earliestHour);
    }
}
