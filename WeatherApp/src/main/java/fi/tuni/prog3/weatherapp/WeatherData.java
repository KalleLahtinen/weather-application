package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherData {
  private static final String API_KEY = "70cbfe7c86c4f2fcdd9fb493ace70183";
  private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
  
  private String rawData = "";

  public void fetchWeatherData(String location) {
    String urlString = String.format(API_URL, location, API_KEY);
    
    try {
      URL url = new URL(urlString);
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
      
      rawData = response.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void parseWeatherData() {
    try {
      // Parse JSON response
      JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
      
      // Get current temperature and feels like temperature
      JsonObject mainObject = jsonObject.getAsJsonObject("main");
      double temperature = mainObject.get("temp").getAsDouble();
      double feelsLike = mainObject.get("feels_like").getAsDouble();
      
      // Get the speed of wind
      JsonObject windObject = jsonObject.getAsJsonObject("wind");
      double windSpeed = windObject.get("speed").getAsDouble();
        
      System.out.println("London:");
      System.out.println(temperature);
      System.out.println(feelsLike);
      System.out.println(windSpeed);
      
      
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
