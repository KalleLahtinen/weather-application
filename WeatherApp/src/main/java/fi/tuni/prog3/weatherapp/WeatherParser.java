package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class WeatherParser {
    public ArrayList<String> parseLookUpLocation(String rawData) {
        try {
            JsonArray jsonArray = JsonParser.parseString(rawData).getAsJsonArray();
            
            JsonObject locationObject = jsonArray.get(0).getAsJsonObject();
            String latitude = locationObject.get("lat").getAsString();
            String longitude = locationObject.get("lon").getAsString();
            
            ArrayList<String> coordinates = new ArrayList<String>();
            
            coordinates.add(latitude);
            coordinates.add(longitude);
            
            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Weather parseCurrentWeather(String rawData) {
        try {
            // Parse JSON response in String
            JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
            
            // Get longitude and latitude
            JsonObject coordObject = jsonObject.getAsJsonObject("coord");
            double longitude = coordObject.get("lon").getAsDouble();
            double latitude = coordObject.get("lat").getAsDouble();
            
            // Get current weather description
            JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
            JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
            
            String mainWeather = weatherObject.get("main").getAsString();
            String weatherDescription = weatherObject.get("description").getAsString();
            
            // Get current temperature, feels like temperature, min and max
            JsonObject mainObject = jsonObject.getAsJsonObject("main");
            double temperature = mainObject.get("temp").getAsDouble();
            double feelsLike = mainObject.get("feels_like").getAsDouble();
            double minTemp = mainObject.get("temp_min").getAsDouble();
            double maxTemp = mainObject.get("temp_max").getAsDouble();
            
            // Get the wind speed
            JsonObject windObject = jsonObject.getAsJsonObject("wind");
            double windSpeed = windObject.get("speed").getAsDouble();
            
            // Initialize rain with default value
            double rainAmount = 0.0;
            
            
            if (mainWeather.equals("Rain")) {
                JsonObject rainObject = jsonObject.getAsJsonObject("rain");
                rainAmount = rainObject.get("1h").getAsDouble();
            }
            
            return new Weather(
                        longitude,
                        latitude,
                        mainWeather,
                        weatherDescription,
                        temperature,
                        minTemp,
                        maxTemp,
                        feelsLike,
                        windSpeed,
                        rainAmount
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Weather> parseForecast(String rawData) {
        // Parse raw data into a list of Weather objects for forecast
        // Example: extract temperature, humidity, weather description for each forecast period
        List<Weather> forecast = new ArrayList<>();
        // Populate forecast list with parsed data
        return forecast;
    }
}
