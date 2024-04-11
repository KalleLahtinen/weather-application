package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData implements iAPI {
    private static final String API_KEY = "70cbfe7c86c4f2fcdd9fb493ace70183"; // Replace with your actual API key
    private static final String GEOLOCATION_API = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String CURRENT_WEATHER_API = "http://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_API = "http://api.openweathermap.org/data/2.5/forecast";
    
    @Override
    public String lookUpLocation(String loc) {
        try {
            String url = String.format("%s?q=%s&limit=1&appid=%s", GEOLOCATION_API, loc, API_KEY);
            return fetchDataFromAPI(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String getCurrentWeather(double lat, double lon) {
        try {
            String url = String.format("%s?lat=%s&lon=%s&appid=%s", CURRENT_WEATHER_API, lat, lon, API_KEY);
            return fetchDataFromAPI(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getForecast(double lat, double lon) {
        try {
            String url = String.format("%s?lat=%s&lon=%s&appid=%s", FORECAST_API, lat, lon, API_KEY);
            return fetchDataFromAPI(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
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
        System.out.println(response.toString());
        return response.toString();
    }
}
