package fi.tuni.prog3.weatherapp;

import javafx.scene.layout.VBox;
import java.util.TreeMap;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private final ObjectProperty<DailyWeather> todayWeather = new SimpleObjectProperty<>();
    private final MapProperty<Instant, DailyWeather> dailyWeathers = 
            new SimpleMapProperty<>(FXCollections.observableHashMap());

    private final List<ObjectProperty<DailyWeather>> displayedDays = new ArrayList<>();
    private final VBox view;
    
    private ForecastViewHourlySection hourlySection;
    
    // Use AtomicReference to safely store reference to current selection
    public AtomicReference<VBox> selectedDay = new AtomicReference<>();

    /**
     * Constructs a ForecastView instance with the given daily and hourly weather data maps.
     * 
     * @param newDailyWeathers a map of daily weather data keyed by date.
     * @param newHourlyWeathers a map of hourly weather data keyed by time.
     */
    public ForecastView(Map<Instant, DailyWeather> newDailyWeathers, 
                        Map<Instant, HourlyWeather> newHourlyWeathers) {
        updateDailyWeathers(newDailyWeathers);
        
        this.hourlySection = new ForecastViewHourlySection(this, newHourlyWeathers);
        hourlySection.updateHourlyWeathers(newHourlyWeathers);
        
        view = initForecastView();
    }

    /**
     * Initializes and returns the main view containing the weather forecast sections.
     * Currently, only the daily weather section is included, but more sections can be added as needed.
     *
     * @return the combined VBox containing all sections of the weather forecast.
     */
    public VBox initForecastView() {
        VBox currentSection = createCurrentWeatherSection();
        HBox dailySection = createDailyWeatherSection();
        ScrollPane hourly = hourlySection.createHourlyWeatherSection();

        // Combine all sections into a single VBox
        VBox root = new VBox();
        root.getChildren().addAll(currentSection, dailySection, hourly);
        root.setAlignment(Pos.CENTER);

        return root;
    }

    /**
     * Creates and returns a VBox containing weather information for the current day.
     * This includes temperature, "feels like" temperature, rain volume, and wind speed.
     *
     * @return a VBox filled with Text nodes displaying weather data for current day.
     */
    private VBox createCurrentWeatherSection() {
        VBox CurrWeatherBox = new VBox(10);
        CurrWeatherBox.setAlignment(Pos.CENTER);
        CurrWeatherBox.setPadding(new Insets(10));
        CurrWeatherBox.getStyleClass().add("curr-box");

        // Day Temperature Text
        Text dayTemperatureText = new Text();
        dayTemperatureText.textProperty().bind(
            Bindings.format("%.1f°C", Bindings.selectDouble(todayWeather, "dayTemp"))
        );
        dayTemperatureText.getStyleClass().add("bold-text");
        CurrWeatherBox.getChildren().add(dayTemperatureText);

        // Feels Like Temperature Text
        Text feelsLikeText = new Text();
        feelsLikeText.textProperty().bind(
            Bindings.format("Feels like: %.1f°C", Bindings.selectDouble(todayWeather, "dayFeelsLike"))
        );
        feelsLikeText.getStyleClass().add("normal-text");
        CurrWeatherBox.getChildren().add(feelsLikeText);

        // HBox for additional weather data
        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);

        Text rainAmountText = new Text();
        rainAmountText.textProperty().bind(
            Bindings.format("Rain: %.1f mm", Bindings.selectDouble(todayWeather, "rainVolume"))
        );
        Text windSpeedText = new Text();
        windSpeedText.textProperty().bind(
            Bindings.format("Wind Speed: %.1f m/s", Bindings.selectDouble(todayWeather, "windSpeed"))
        );

        hbox.getChildren().addAll(rainAmountText, windSpeedText);
        CurrWeatherBox.getChildren().add(hbox);

        return CurrWeatherBox;
    }
    
    /**
     * Creates and returns a HBox containing the daily weather information.
     * This includes min and max temperature for the day.
     *
     * @return a HBox filled with Text nodes displaying daily weather data.
     */
    private HBox createDailyWeatherSection() {
        HBox daysBox = new HBox();
        daysBox.setAlignment(Pos.CENTER);
        daysBox.getStyleClass().add("days-box");

        setupWeatherBindings();  // Initialize bindings

        for (ObjectProperty<DailyWeather> weatherProp : displayedDays) {
            VBox dayBox = new VBox(10);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.getStyleClass().add("day-box");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd.MM")
                                            .withLocale(Locale.getDefault());

            // Bind and display date and temperature range
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.textProperty().bind(Bindings.createStringBinding(
                () -> formatter.format(weatherProp.get().getDate().atZone(ZoneId.systemDefault())),
                weatherProp));

            Label tempRange = new Label();
            tempRange.getStyleClass().add("temp-range");
            tempRange.textProperty().bind(
                    Bindings.format("%.0f°C ... %.0f°C",
                        Bindings.selectDouble(weatherProp, "minTemp"),
                        Bindings.selectDouble(weatherProp, "maxTemp")
                    ));

            dayBox.getChildren().addAll(dateLabel, tempRange);
            daysBox.getChildren().add(dayBox);

            // Click event for selecting a day
            dayBox.setOnMouseClicked(event -> {
                if (selectedDay.get() != null) {
                    selectedDay.get().getStyleClass().remove("selected-day");
                }
                dayBox.getStyleClass().add("selected-day");
                selectedDay.set(dayBox);
            });
        }
        
        // Select the first day by default if it exists
        if (!daysBox.getChildren().isEmpty()) {
            VBox firstDayBox = (VBox) daysBox.getChildren().get(0);
            firstDayBox.getStyleClass().add("selected-day");
            selectedDay.set(firstDayBox);
        }
        
        return daysBox;
    }
    
    /**
     * Binds the items in {@code displayedDays} to the first five days in {@code dailyWeathers}.
     */
    private void setupWeatherBindings() {
        // Assume we care about the first 5 days
        List<Instant> keys = dailyWeathers.keySet().stream()
                .sorted()
                .limit(5)
                .collect(Collectors.toList());

        // Setup or update ObjectProperties for each day
        for (int i = 0; i < keys.size(); i++) {
            Instant key = keys.get(i);
            if (i < displayedDays.size()) {
                // Update existing property
                displayedDays.get(i).set(dailyWeathers.get(key));
            } else {
                // Add new property
                ObjectProperty<DailyWeather> prop = 
                        new SimpleObjectProperty<>(dailyWeathers.get(key));
                displayedDays.add(prop);
            }
        }

        // Adjust list size in case of fewer entries than before
        if (keys.size() < displayedDays.size()) {
            displayedDays.subList(keys.size(), displayedDays.size()).clear();
        }
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
        setupWeatherBindings();  // Update bindings for the HBox with daily weather data
    }
    
    public void updateSections(Map<Instant, DailyWeather> newDailyWeathers,
                               Map<Instant, HourlyWeather> newHourlyWeathers) {
        updateDailyWeathers(newDailyWeathers);
        hourlySection.updateHourlyWeathers(newHourlyWeathers);
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