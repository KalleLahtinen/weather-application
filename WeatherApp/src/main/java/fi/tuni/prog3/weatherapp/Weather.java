package fi.tuni.prog3.weatherapp;

public class Weather {
    private double longitude;
    private double latitude;
    private String mainWeather;
    private String weatherDescription;
    private String iconId;
    private double temperature;
    private double maxTemperature;
    private double minTemperature;
    private double feelsLike;
    private double windSpeed;
    private double rainAmount;
    
    public Weather(
      double longitude,
      double latitude,
      String mainWeather,
      String weatherDescription,
      String iconId,
      double temperature,
      double maxTemperature,
      double minTemperature,
      double feelsLike,
      double windSpeed,
      double rainAmount
    ) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.mainWeather = mainWeather;
        this.weatherDescription = weatherDescription;
        this.iconId = iconId;
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
        this.rainAmount = rainAmount;
    }
    
    public double getLongitude() {
      return longitude;
    }
    
    public double getLatitude() {
      return latitude;
    }
    
    public String getMainWeather() {
      return mainWeather;
    }

    public String getWeatherDescription() {
      return weatherDescription;
    }

    public String getIconId() {
      return iconId;
    }

    public double getTemperature() {
      return temperature;
    }
    
    public double getMaxTemperature() {
      return maxTemperature;
    }
    
    public double getMinTemperature() {
      return minTemperature;
    }

    public double getFeelsLike() {
      return feelsLike;
    }

    public double getWindSpeed() {
      return windSpeed;
    }

    public double getRainAmount() {
      return rainAmount;
    }
}
