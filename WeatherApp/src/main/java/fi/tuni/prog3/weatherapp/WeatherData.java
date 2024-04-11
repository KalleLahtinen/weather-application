package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Class implementing the iAPI interface to interact with OpenWeatherMap API
public class WeatherData implements iAPI {
    // Instance of WeatherParser to parse API responses
    private WeatherParser weatherParser;
    
    // Constructor initializing WeatherParser
    public WeatherData() {
        this.weatherParser = new WeatherParser();
    }
    
    // API key for OpenWeatherMap
    private static final String API_KEY = "70cbfe7c86c4f2fcdd9fb493ace70183"; // Replace with your actual API key
    
    // URLs for different API endpoints
    private static final String GEOLOCATION_API = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String CURRENT_WEATHER_API = "http://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast/daily";
    
    @Override
    public ArrayList<Double> lookUpLocation(String loc) {
        try {
            String url = String.format("%s?q=%s&limit=1&appid=%s", GEOLOCATION_API, loc, API_KEY);
            String rawData = fetchDataFromAPI(url);
            return weatherParser.parseLookUpLocation(rawData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Method to look up location coordinates based on location name
    @Override
    public Weather getCurrentWeather(double lat, double lon) {
        try {
            String url = String.format("%s?lat=%s&lon=%s&appid=%s", CURRENT_WEATHER_API, lat, lon, API_KEY);
            
            // Fetch data from API
            String rawData = fetchDataFromAPI(url);
            
            // Parse and return location data
            return weatherParser.parseCurrentWeather(rawData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Method to get current weather based on latitude and longitude
    @Override
    public Map<String, List<Double>> getForecast(double lat, double lon) {
        try {
            String url = String.format("%s?lat=%s&lon=%s&appid=%s&units=%s&cnt=%s", FORECAST_API, lat, lon, API_KEY, "metric", 6);
            
            // Fetch data from API
            String rawData = fetchDataFromAPI(url);
            
            // Parse and return current weather data
            return weatherParser.parseForecast(rawData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to fetch data from API
    private String fetchDataFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        } 
        in.close();
        connection.disconnect();

        // Return API response
        return response.toString();
    }
}
