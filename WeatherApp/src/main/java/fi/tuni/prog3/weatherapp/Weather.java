package fi.tuni.prog3.weatherapp;

public class Weather {
    private String mainWeather;
    private String weatherDescription;
    private String iconId;
    private double temperature;
    private double feelsLike;
    private double windSpeed;
    private double rainAmount;
    
    public Weather(
      String mainWeather,
      String weatherDescription,
      String iconId,
      double temperature,
      double feelsLike,
      double windSpeed,
      double rainAmount
    ) {
        this.mainWeather = mainWeather;
        this.weatherDescription = weatherDescription;
        this.iconId = iconId;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
        this.rainAmount = rainAmount;
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
