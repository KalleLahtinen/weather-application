package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

/**
 * Class implementing the iAPI interface to interact with OpenWeatherMap API
 */
public class WeatherDataService implements iAPI {
    
    // API key for OpenWeatherMap
    private static final String API_KEY = "70cbfe7c86c4f2fcdd9fb493ace70183";
    
    // URLs for different API endpoints
    private static final String GEOLOCATION_API = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String DAILY_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily";
    private static final String HOURLY_FORECAST_API = "https://pro.openweathermap.org/data/2.5/forecast/hourly";
    
    /**
     * Get coordinates for a location by name.
     * @param loc Name of the location for which weather should be fetched.
     * @return The coordinates as a Coordinate object
     */
    @Override
    public Coordinate getCoordinates(String loc) {
        try {
            String url = String.format("%s?q=%s&limit=1&appid=%s", GEOLOCATION_API, loc, API_KEY);
            String jsonResponse = fetchDataFromAPI(url);
            
            // Parse JSON response
            JsonObject locationObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            
            // Extract latitude and longitude  
            double latitude = locationObject.get("lat").getAsDouble();
            double longitude = locationObject.get("lon").getAsDouble();
            Coordinate coords = new Coordinate(latitude, longitude);
            
            return coords;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Returns a daily forecast for the given location.
     * @param loc Name of the location for which weather should be fetched.
     * @param units The units of measurement the data is to be represented in.
     * @return A forecast as a map of Instant and DailyWeather pairs.
     */
    @Override
    public Map<Instant, DailyWeather> getDailyForecast(String loc, String units) {
        try {
            Coordinate coords = getCoordinates(loc);          
            String url = String.format("%s?lat=%s&lon=%s&appid=%s&units=%s&cnt=%s", 
                    DAILY_FORECAST_API, coords.getLatitude(), coords.getLongitude(), 
                    API_KEY, "metric", 6);
            
            // Fetch data from API
            String jsonResponse = fetchDataFromAPI(url);
            
            // Parse and return current weather data
            return WeatherParser.parseDailyForecast(jsonResponse, units);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Returns a hourly forecast for the given location.
     * @param loc Name of the location for which weather should be fetched.
     * @param units The units of measurement the data is to be represented in.
     * @return A map of Instant and HourlyWeather pairs.
     */
    @Override
    public Map<Instant, HourlyWeather> getHourlyForecast(String loc, String units) {
        try {
            Coordinate coords = getCoordinates(loc);          
            String url = String.format("%s?lat=%s&lon=%s&appid=%s&units=%s", HOURLY_FORECAST_API, 
                    coords.getLatitude(), coords.getLongitude(), API_KEY, units);
            
            // Fetch data from API
            String jsonResponse = fetchDataFromAPI(url);

            // Parse and return location data
            return WeatherParser.parseHourlyForecast(jsonResponse, units);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * A method for fetching and returning a Json API response in string format
     * @param apiUrl The API's url for fetching Json responses
     * @return A Json API response containing in string format
     * @throws IOException On issues during HTTP connection
     */
    public String fetchDataFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        StringBuilder response;
        // Use try-with-resources to close stream
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
        }
        connection.disconnect();

        // Return API response
        return response.toString();
    }
}
