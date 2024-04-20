package fi.tuni.prog3.weatherapp;

import javafx.scene.layout.VBox;
import java.util.TreeMap;
import java.time.Instant;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/*
 * ChatGPT 3.5 was heavily utilized in learning the usage of JavaFX
 * properties and iterating methods until solutions were found. This has enabled
   updating screen content dynamically without recreating all elements.
 */

/**
 * Class for creating the applications forecast view, encapsulating 
 * current, daily and hourly forecasts.
 * 
 * @author Kalle Lahtinen
 */
public final class ForecastView {
    // Property values that update binded elements when value changes
    private ObjectProperty<DailyWeather> todayWeather = new SimpleObjectProperty<>();
    private MapProperty<Instant, DailyWeather> dailyWeathers = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private MapProperty<Instant, HourlyWeather> hourlyWeathers = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private final VBox view;

    /**
     * Constructs a ForecastView instance with the given daily and hourly weather data maps.
     * 
     * @param newDailyWeathers a map of daily weather data keyed by date.
     * @param newHourlyWeathers a map of hourly weather data keyed by time.
     */
    public ForecastView(Map<Instant, DailyWeather> newDailyWeathers, 
                        Map<Instant, HourlyWeather> newHourlyWeathers) {
        updateDailyWeathers(newDailyWeathers);
        updateHourlyWeathers(newHourlyWeathers);
        view = initForecastView();
    }

    /**
     * Initializes and returns the main view containing the weather forecast sections.
     * Currently, only the daily weather section is included, but more sections can be added as needed.
     *
     * @return the combined VBox containing all sections of the weather forecast.
     */
    public VBox initForecastView() {
        VBox dailySection = createDailyWeatherSection();
        //VBox hourlySection = createHourlyWeatherSection();
        // More sections can be added here if needed

        // Combine all sections into a single VBox
        VBox root = new VBox(10); // You can adjust spacing as needed
        root.getChildren().addAll(dailySection);
        root.setAlignment(Pos.CENTER);

        return root;
    }

    /**
     * Creates and returns the VBox containing the daily weather information.
     * This includes temperature, "feels like" temperature, air quality, rain volume, and wind speed.
     *
     * @return a VBox filled with Text nodes displaying daily weather data.
     */
    private VBox createDailyWeatherSection() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        // Day Temperature Text
        Text dayTemperatureText = new Text();
        dayTemperatureText.textProperty().bind(
            Bindings.format("%.1f°C", Bindings.selectDouble(todayWeather, "dayTemp"))
        );
        dayTemperatureText.getStyleClass().add("bold-text");
        vbox.getChildren().add(dayTemperatureText);

        // Feels Like Temperature Text
        Text feelsLikeText = new Text();
        feelsLikeText.textProperty().bind(
            Bindings.format("Feels like: %.1f°C", Bindings.selectDouble(todayWeather, "dayFeelsLike"))
        );
        feelsLikeText.getStyleClass().add("normal-text");
        vbox.getChildren().add(feelsLikeText);

        // HBox for additional weather data
        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);

        Text airQualityText = new Text("Air Quality: Good"); // Placeholder
        Text rainAmountText = new Text();
        rainAmountText.textProperty().bind(
            Bindings.format("Rain: %.1f mm", Bindings.selectDouble(todayWeather, "rainVolume"))
        );
        Text windSpeedText = new Text();
        windSpeedText.textProperty().bind(
            Bindings.format("Wind Speed: %.1f km/h", Bindings.selectDouble(todayWeather, "windSpeed"))
        );

        hbox.getChildren().addAll(airQualityText, rainAmountText, windSpeedText);
        vbox.getChildren().add(hbox);

        return vbox;
    }

    /**
     * Updates the daily weather data with the given map and 
     * sets the current weather to the earliest date available.
     *
     * @param newDailyWeathers the new map of daily weather data.
     */
    public void updateDailyWeathers(Map<Instant, DailyWeather> newDailyWeathers) {
        dailyWeathers.clear();
        dailyWeathers.putAll(newDailyWeathers);
        if (!dailyWeathers.isEmpty()) {
            todayWeather.set(new TreeMap<>(dailyWeathers).firstEntry().getValue());
        }
    }

    /**
     * Updates the hourly weather data with the given map.
     *
     * @param newHourlyWeathers the new map of hourly weather data.
     */
    public void updateHourlyWeathers(Map<Instant, HourlyWeather> newHourlyWeathers) {
        hourlyWeathers.clear();
        hourlyWeathers.putAll(newHourlyWeathers);
    }
    
    /**
     * Retrieves the primary view of the weather forecast.
     *
     * @return the VBox representing the visual layout of the forecast.
     */
    public VBox getView() {
        return view;
    }
}