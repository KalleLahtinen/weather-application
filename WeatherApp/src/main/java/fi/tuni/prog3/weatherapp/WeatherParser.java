package fi.tuni.prog3.weatherapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

/*
    ChatGPT 3.5 was heavily utilized in the creation and documentation of this class 
    to refactor code and create Javadoc comments.
*/

/**
 * A class containing parse functions for turning a JSON response into weather 
 * objects.
 * 
 * @author Roope Kärkkäinen and Kalle Lahtinen
 */
public class WeatherParser {
    /**
     * A method for parsing a JSON string into individual DailyWeather objects
     * @param jsonResponse The JSON response string containing weather data
     * @param units The units of measurement the data is to be represented in.
     * 
     * @return A Map of DailyWeather objects sorted by time as Instant
     */
    public static Map<Instant, DailyWeather> parseDailyForecast(String jsonResponse, String units) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray days = jsonObject.getAsJsonArray("list");
        // Save the DailyWeather objects in a map sorted by time as Instant
        Map<Instant, DailyWeather> weatherMap = new HashMap<>();

        // Create a DailyWeather object for every day in json response
        for (int i = 0; i < days.size(); i++) {
            // Use Instant to represent universal UTC time, as with API
            JsonObject day = days.get(i).getAsJsonObject();
            Instant date = Instant.ofEpochSecond(day.get("dt").getAsLong());
            JsonObject temp = day.getAsJsonObject("temp");
            JsonObject feelsLike = day.getAsJsonObject("feels_like");
            
            DailyWeather weather = new DailyWeather(
                    date, 
                    temp.get("day").getAsDouble(), 
                    temp.get("min").getAsDouble(), 
                    temp.get("max").getAsDouble(), 
                    feelsLike.get("day").getAsDouble(), 
                    day.get("speed").getAsDouble(), 
                    day.has("rain") ? day.get("rain").getAsDouble() : 0.0,
                    // Get weather description
                    day.getAsJsonArray("weather").get(0)
                    .getAsJsonObject().get("description").getAsString(),
                    // Get weather icon id
                    WeatherIconManager.getIconCode(
                            day.getAsJsonArray("weather").get(0)
                            .getAsJsonObject().get("id").getAsInt(), true),
                    // Also save the used units of measurement in object
                    units
            );
            weatherMap.put(date, weather);
        }
        return weatherMap;
    }
    
    /**
     * A method for parsing a JSON string into individual HourlyWeather objects
     * @param jsonResponse The JSON response string containing weather data
     * @param units The units of measurement the data is to be represented in.
     * 
     * @return A Map of HourlyWeather objects sorted by time as Instant
     */
    public static Map<Instant, HourlyWeather> parseHourlyForecast(String jsonResponse, String units) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray hours = jsonObject.getAsJsonArray("list");
        // Save the HourlyWeather objects in a map sorted by time as Instant
        Map<Instant, HourlyWeather> weatherData = new HashMap<>();
        
        // Create a DailyWeather object for every day in json response
        for (int i = 0; i < hours.size(); i++) {
            JsonObject element = hours.get(i).getAsJsonObject();
            JsonObject main = element.getAsJsonObject("main");
            JsonObject wind = element.getAsJsonObject("wind");
            JsonArray weatherArray = element.getAsJsonArray("weather");
            JsonObject weather = weatherArray.get(0).getAsJsonObject();
            JsonObject rain = element.getAsJsonObject("rain");
            
            // Use Instant to represent universal UTC time, as with API
            long epochSeconds = element.get("dt").getAsLong();
            Instant date = Instant.ofEpochSecond(epochSeconds);
            
            HourlyWeather hourlyWeather = new HourlyWeather(
                    date,
                    main.get("temp").getAsDouble(),
                    main.get("feels_like").getAsDouble(),
                    main.get("pressure").getAsInt(),
                    main.get("humidity").getAsInt(),
                    wind.get("speed").getAsDouble(),
                    wind.get("deg").getAsInt(),
                    weather.get("description").getAsString(),
                    weather.get("icon").getAsString(),
                    // Take into consideration places with no rain
                    rain != null ? rain.get("1h").getAsDouble() : 0.0,
                    // Get weather icon id
                    WeatherIconManager.getIconCode(
                            element.getAsJsonArray("weather").get(0)
                            .getAsJsonObject().get("id").getAsInt(), true),
                    // Also save the used units of measurement in object
                    units
            );
            weatherData.put(date, hourlyWeather);
        }
        return weatherData;
    }
}