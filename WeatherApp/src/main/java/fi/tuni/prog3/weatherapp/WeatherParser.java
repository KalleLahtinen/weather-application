package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;


public class WeatherParser {
    public ArrayList<Double> parseLookUpLocation(String rawData) {
        try {
            JsonArray jsonArray = JsonParser.parseString(rawData).getAsJsonArray();
            
            JsonObject locationObject = jsonArray.get(0).getAsJsonObject();
            double latitude = locationObject.get("lat").getAsDouble();
            double longitude = locationObject.get("lon").getAsDouble();
            
            ArrayList<Double> coordinates = new ArrayList<Double>();

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
            // Parse JSON response
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
        System.out.println(rawData);
        // Parse raw data into a list of Weather objects for forecast
        // Example: extract temperature, humidity, weather description for each forecast period
        
        List<Weather> forecast = new ArrayList<>();
        
        // Parse JSON response
        JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
        
        JsonArray daysArray = jsonObject.getAsJsonArray("list");
        
        for (int i = 0; i < daysArray.size(); i++) {
            
            JsonObject dayObject = daysArray.get(i).getAsJsonObject();
            
            long timestamp = dayObject.get("dt").getAsLong();
            
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault()
            );
                        
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");                        
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

            String formattedDate = dayOfWeek + " " + dateTime.format(formatter);

            System.out.println(formattedDate);
             
            JsonObject mainObject = dayObject.getAsJsonObject("temp");
            double minTemp = mainObject.get("min").getAsDouble();
            double maxTemp = mainObject.get("max").getAsDouble();
            
            Map<String, List<Weather>> days = new HashMap<>();
        }
        

        // Populate forecast list with parsed data
        return forecast;
    }
}
