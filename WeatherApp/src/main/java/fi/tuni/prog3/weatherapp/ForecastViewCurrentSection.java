package fi.tuni.prog3.weatherapp;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Kalle Lahtinen
 */
public class ForecastViewCurrentSection {
    
    /**
     * Creates and returns a VBox containing weather information for the current day.
     * This includes temperature, "feels like" temperature, rain volume, and wind speed.
     *
     * @return a VBox filled with Text nodes displaying weather data for current day.
     */
    VBox createCurrentWeatherSection(ForecastView forecastView) {
        VBox CurrWeatherBox = new VBox(10);
        CurrWeatherBox.setAlignment(Pos.CENTER);
        CurrWeatherBox.setPadding(new Insets(10));
        CurrWeatherBox.getStyleClass().add("curr-box");
        
        // Day Temperature Text
        Text dayTemperatureText = new Text();
        dayTemperatureText.textProperty().
                bind(Bindings.format("%.1f\u00b0C",
                Bindings.selectDouble(forecastView.todayWeather, "dayTemp")));
        dayTemperatureText.getStyleClass().add("bold-text");
        CurrWeatherBox.getChildren().add(dayTemperatureText);
        
        // Feels Like Temperature Text
        Text feelsLikeText = new Text();
        feelsLikeText.textProperty().
                bind(Bindings.format("Feels like: %.1f\u00b0C",
                Bindings.selectDouble(forecastView.todayWeather, "dayFeelsLike")));
        feelsLikeText.getStyleClass().add("normal-text");
        CurrWeatherBox.getChildren().add(feelsLikeText);
        
        // HBox for additional weather data
        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);
        
        Text rainAmountText = new Text();
        rainAmountText.textProperty().
                bind(Bindings.format("Rain: %.1f mm",
                Bindings.selectDouble(forecastView.todayWeather, "rainVolume")));
        
        Text windSpeedText = new Text();
        windSpeedText.textProperty().
                bind(Bindings.format("Wind Speed: %.1f m/s",
                Bindings.selectDouble(forecastView.todayWeather, "windSpeed")));
        
        hbox.getChildren().addAll(rainAmountText, windSpeedText);
        CurrWeatherBox.getChildren().add(hbox);
        
        return CurrWeatherBox;
    }
    
}
