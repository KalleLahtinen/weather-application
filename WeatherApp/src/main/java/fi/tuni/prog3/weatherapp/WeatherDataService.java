package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

/**
 * Class for interacting with OpenWeatherMap API and returning weather data
 * 
 * @author Roope Kärkkäinen and Kalle Lahtinen
 */
public class WeatherDataService implements iAPI {
    
    // API key for OpenWeatherMap
    private static final String API_KEY = "70cbfe7c86c4f2fcdd9fb493ace70183";
    
    // URLs for different API endpoints
    private static final String GEOLOCATION_API = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String DAILY_FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily";
    private static final String HOURLY_FORECAST_API = "https://pro.openweathermap.org/data/2.5/forecast/hourly";
    
    /**
     * Check if there is a city with this name in some language.
     * @param query The search term for a city.
     * @return The name of the queried city if found, null otherwise.
     */
    public String getCity(String query) {
        if (query.length() > 0) {
            try {
                String url = String.format("%s?q=%s&limit=1&appid=%s", GEOLOCATION_API, query, API_KEY);
                String jsonResponse = fetchDataFromAPI(url);
                
                // Parse JSON response
                JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
                
                if (jsonArray.size() == 0) {
                    return null;
                }

                // Extract city name in English
                JsonObject locationObject = jsonArray.get(0).getAsJsonObject();
                String cityName = locationObject.get("name").getAsString();

                return cityName;

            } catch (IOException | IndexOutOfBoundsException e) {
                String errorMessage = "Error fetching city information from " + query;
                LoggingInformation.logError(errorMessage, e);
                return null;
            }
        }
        return null;
    }
    
    /**
     * Get coordinates for a location by name.
     * 
     * @param loc Name of the location for which weather should be fetched.
     * @return The coordinates as a Coordinate object
     */
    @Override
    public Coordinate getCoordinates(String loc) {
        if (loc.length() > 0) {
            try {
                String url = String.format("%s?q=%s&limit=1&appid=%s", GEOLOCATION_API, loc, API_KEY);
                String jsonResponse = fetchDataFromAPI(url);

                // Parse JSON response
                JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();

                // Extract latitude and longitude
                JsonObject locationObject = jsonArray.get(0).getAsJsonObject();

                double latitude = locationObject.get("lat").getAsDouble();
                double longitude = locationObject.get("lon").getAsDouble();
                Coordinate coords = new Coordinate(latitude, longitude);

                return coords;

            } catch (IOException | IndexOutOfBoundsException e) {
                String errorMessage = "Error fetching location information from " + loc;
                LoggingInformation.logError(errorMessage, e);
                return null;
            }           
        }
        return null;
    }
    
    /**
     * Returns a daily forecast for the given location.
     * 
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
                    API_KEY, units, 6);
            
            // Fetch data from API
            String jsonResponse = fetchDataFromAPI(url);
            
            // Parse and return current weather data
            return WeatherParser.parseDailyForecast(jsonResponse, units);
            
        } catch (IOException e) {
            String errorMessage = "Error fetching daily forecast for " 
                    + loc + " in " + units;
            LoggingInformation.logError(errorMessage, e);
            return null;
        }
    }
    
    /**
     * Returns a hourly forecast for the given location.
     * 
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
            
            String jsonResponse = fetchDataFromAPI(url);

            // Parse and return location data
            return WeatherParser.parseHourlyForecast(jsonResponse, units);
            
        } catch (IOException e) {
            String errorMessage = "Error fetching hourly forecast for " + loc 
                    + " in " + units;
            LoggingInformation.logError(errorMessage, e);
            return null;
        }
    }

    /**
     * A method for fetching and returning a JSON API response in string format
     * @param apiUrl The API's URL for fetching JSON responses
     * @return A JSON API response containing in string format
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

        return response.toString();
    }
}
