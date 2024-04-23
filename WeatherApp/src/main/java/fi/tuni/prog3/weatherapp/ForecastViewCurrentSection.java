package fi.tuni.prog3.weatherapp;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
 * A class for creating the current weather section of the forecast view with 
 * styling and values binded to corresponding data in forecastView.
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
    VBox createCurrentWeatherSection(ForecastViewController forecastView) {
        VBox CurrWeatherBox = new VBox(10);
        CurrWeatherBox.setAlignment(Pos.CENTER);
        CurrWeatherBox.setPadding(new Insets(10));
        CurrWeatherBox.getStyleClass().add("curr-box");
        
        // Day Temperature Text
        Text dayTemperatureText = new Text();
        dayTemperatureText.textProperty().
                bind(Bindings.format("%.1f%s",
                Bindings.selectDouble(forecastView.todayWeather, "dayTemp"),
                Bindings.selectString(forecastView.measurementSystem, "tempUnit")));
        dayTemperatureText.getStyleClass().add("bold-text");
        CurrWeatherBox.getChildren().add(dayTemperatureText);
        
        Text weatherIconText = new Text();
        weatherIconText.setFont(new Font("Weather Icons", 40)); // Ensure the font is correctly loaded
        weatherIconText.textProperty().
                bind(Bindings.format("%s",
                Bindings.selectString(forecastView.todayWeather, "iconCode")));
        CurrWeatherBox.getChildren().add(weatherIconText);
        
        // Feels Like Temperature Text
        Text feelsLikeText = new Text();
        feelsLikeText.textProperty().
                bind(Bindings.format("Feels like: %.1f%s",
                Bindings.selectDouble(forecastView.todayWeather, "dayFeelsLike"),
                Bindings.selectString(forecastView.measurementSystem, "tempUnit")));
        feelsLikeText.getStyleClass().add("normal-text");
        CurrWeatherBox.getChildren().add(feelsLikeText);
        
        // HBox for additional weather data
        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);
        
        Text rainAmountText = new Text();
        rainAmountText.textProperty().
                bind(Bindings.format("Rain: %.1f %s",
                Bindings.selectDouble(forecastView.todayWeather, "rainVolume"),
                Bindings.selectString(forecastView.measurementSystem, "rainUnit")));
        
        Text windSpeedText = new Text();
        windSpeedText.textProperty().
                bind(Bindings.format("Wind Speed: %.1f %s",
                Bindings.selectDouble(forecastView.todayWeather, "windSpeed"),
                Bindings.selectString(forecastView.measurementSystem, "windUnit")));
        
        hbox.getChildren().addAll(rainAmountText, windSpeedText);
        CurrWeatherBox.getChildren().add(hbox);
        
        return CurrWeatherBox;
    }
}
