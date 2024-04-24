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
        
        Text currentWeatherText = new Text("Current Weather");
        currentWeatherText.getStyleClass().add("bold-text");
        
        // Create a container for weather icon and day temperature
        HBox tempBox = new HBox();
        tempBox.setAlignment(Pos.CENTER);
        
        Text currentWeatherIcon = new Text();
        currentWeatherIcon.setFont(new Font("Weather Icons", 50));
        currentWeatherIcon.textProperty().bind(Bindings.format("%s",
                Bindings.selectString(forecastView.todayWeather, "iconCode")));
        HBox.setMargin(currentWeatherIcon, new Insets(0, 15, 0, 0)); // Top, Right, Bottom, Left
        currentWeatherIcon.setId("currentWeatherIcon");
        
        // Day Temperature Text
        Text currentTemperature = new Text();
        currentTemperature.textProperty().bind(Bindings.format("%.1f",
                Bindings.selectDouble(forecastView.todayWeather, "dayTemp")));
        currentTemperature.setId("currentTemperature");
        
        Text currentDegree = new Text();
        currentDegree.textProperty().bind(Bindings.format("%s",
                Bindings.selectString(forecastView.measurementSystem, "tempUnit")));
        currentDegree.setTranslateY(-12);
        currentDegree.setId("currentDegree");
        
        tempBox.getChildren().addAll(currentWeatherIcon, currentTemperature, currentDegree);
                
        // Feels Like Temperature Text
        Text feelsLikeText = new Text();
        feelsLikeText.textProperty().bind(Bindings.format("Feels like: %.1f %s",
                Bindings.selectDouble(forecastView.todayWeather, "dayFeelsLike"),
                Bindings.selectString(forecastView.measurementSystem, "tempUnit")));
        feelsLikeText.getStyleClass().add("detail-text");
        
        // HBox for additional weather data
        HBox detailBox = new HBox(15);
        detailBox.setAlignment(Pos.CENTER);
        
        Text rainAmountText = new Text();
        rainAmountText.textProperty().bind(Bindings.format("Rain: %.1f %s",
                Bindings.selectDouble(forecastView.todayWeather, "rainVolume"),
                Bindings.selectString(forecastView.measurementSystem, "rainUnit")));
        rainAmountText.getStyleClass().add("detail-text");
        
        Text windSpeedText = new Text();
        windSpeedText.textProperty().bind(Bindings.format("Wind Speed: %.1f %s",
                Bindings.selectDouble(forecastView.todayWeather, "windSpeed"),
                Bindings.selectString(forecastView.measurementSystem, "windUnit")));
        windSpeedText.getStyleClass().add("detail-text");
        
        detailBox.getChildren().addAll(rainAmountText, windSpeedText);
        
        // Add all created elements to current weather container
        CurrWeatherBox.getChildren().addAll(currentWeatherText, tempBox, feelsLikeText, detailBox);
        
        return CurrWeatherBox;
    }
}
