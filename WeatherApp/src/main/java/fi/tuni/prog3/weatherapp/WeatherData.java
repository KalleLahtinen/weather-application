package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
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
      
      // Get current weather description
      JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
      JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
      
      String mainWeather = weatherObject.get("main").getAsString();
      String weatherDescription = weatherObject.get("description").getAsString();
      String iconId = weatherObject.get("icon").getAsString();
      
      // Get current temperature and feels like temperature
      JsonObject mainObject = jsonObject.getAsJsonObject("main");
      double temperature = mainObject.get("temp").getAsDouble();
      double feelsLike = mainObject.get("feels_like").getAsDouble();
      
      // Get the wind speed
      JsonObject windObject = jsonObject.getAsJsonObject("wind");
      double windSpeed = windObject.get("speed").getAsDouble();
      
      // Initialize rain with default value
      double rainAmount = 0.0;
      
      // If it's raining get rain in millimeters
      if (mainWeather.equals("Rain")) {
        JsonObject rainObject = jsonObject.getAsJsonObject("rain");
        rainAmount = rainObject.get("1h").getAsDouble();
      }
      
      Weather finalObject = new Weather(
        mainWeather,
        weatherDescription,
        iconId,
        temperature,
        feelsLike,
        windSpeed,
        rainAmount
      );
      
      System.out.println(finalObject);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
