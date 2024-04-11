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
    
    // Method to parse location data from the API response
    public ArrayList<Double> parseLookUpLocation(String rawData) {
        try {
            // Parse JSON response
            JsonArray jsonArray = JsonParser.parseString(rawData).getAsJsonArray();
            
            // Extract latitude and longitude
            JsonObject locationObject = jsonArray.get(0).getAsJsonObject();
            double latitude = locationObject.get("lat").getAsDouble();
            double longitude = locationObject.get("lon").getAsDouble();
            
            // Create an ArrayList to store coordinates and add latitude and longitude
            ArrayList<Double> coordinates = new ArrayList<Double>();
            coordinates.add(latitude);
            coordinates.add(longitude);
            
            // TODO: Check for enum, pair etc.
            return coordinates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Method to parse current weather data from the API response
    public Weather parseCurrentWeather(String rawData) {
        try {
            // Parse JSON response
            JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
            
            // Extract longitude and latitude
            JsonObject coordObject = jsonObject.getAsJsonObject("coord");
            double longitude = coordObject.get("lon").getAsDouble();
            double latitude = coordObject.get("lat").getAsDouble();
            
            // Extract current weather description
            JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
            JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
            
            String mainWeather = weatherObject.get("main").getAsString();
            String weatherDescription = weatherObject.get("description").getAsString();
            
            // Extract current temperature, feels like temperature, min and max
            JsonObject mainObject = jsonObject.getAsJsonObject("main");
            double temperature = mainObject.get("temp").getAsDouble();
            double feelsLike = mainObject.get("feels_like").getAsDouble();
            double minTemp = mainObject.get("temp_min").getAsDouble();
            double maxTemp = mainObject.get("temp_max").getAsDouble();
            
            // Extract wind speed
            JsonObject windObject = jsonObject.getAsJsonObject("wind");
            double windSpeed = windObject.get("speed").getAsDouble();
            
            // Initialize rain with default value
            double rainAmount = 0.0;
            
            // Check if it's raining and extract rain amount
            if (mainWeather.equals("Rain")) {
                JsonObject rainObject = jsonObject.getAsJsonObject("rain");
                rainAmount = rainObject.get("1h").getAsDouble();
            }
            
            // Create and return a Weather object with the extracted data
            // Jokainen Mapin avain vastaisi yhtä arvoa
            
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
    
    // Method to parse forecast data from the API response
    public Map<String, List<Double>> parseForecast(String rawData) {
        Map<String, List<Double>> days = new HashMap<>();
        
        // Parse JSON response
        JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
        
        // Extract forecast data for each day
        JsonArray daysArray = jsonObject.getAsJsonArray("list");
        for (int i = 0; i < daysArray.size(); i++) {   
            JsonObject dayObject = daysArray.get(i).getAsJsonObject();
            
            // Extract timestamp for the day
            long timestamp = dayObject.get("dt").getAsLong();
            
            // Convert integer value 32bit timestamp to LocalDateTime
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault()
            );
                
            // Format date as day of the week and date
            // TODO: DateTime object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");                        
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
            String formattedDate = dayOfWeek + " " + dateTime.format(formatter);
            
            // Extract min and max temperature for the day
            JsonObject mainObject = dayObject.getAsJsonObject("temp");
            double minTemp = mainObject.get("min").getAsDouble();
            double maxTemp = mainObject.get("max").getAsDouble();
        
            // Add temperature range to the map with the formatted date as key
            days.put(formattedDate, new ArrayList<>());
            days.get(formattedDate).add(minTemp);
            days.get(formattedDate).add(maxTemp);     
        }

        return days;
    }
    
    /*
    Palauttaa HashMapin jossa on päivämääräolioita avaimena ja DailyWeather arvon
    HashMap<PVM, DailyWeather>
        - DailyWeather
            - avgTemp
            - minTemp
            - maxTemp
            - HashMap<(0,1,2,3 jne.) HourlyWeather> hourlyWeathers
                - HourlyWeather
                    - HourlyWeather näkymän arvoja
                    - temp
                    - wind jne. jne.
    */
}
