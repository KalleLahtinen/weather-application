package fi.tuni.prog3.weatherapp;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Map;

/**
 * Tests for the methods in WeatherDataService using Mockito to fake API responses
 * @author Kalle Lahtinen
 */
@ExtendWith(MockitoExtension.class)
public class WeatherDataServiceTest {

    @Spy
    private WeatherDataService weatherDataService;

    /**
     * Initializes Mockito before each test method.
     * This method sets up the Mockito environment for spying on the {@link WeatherDataService}.
     * @throws IOException if an IO error occurs during setup
     */
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }
    
    /**
     * Tests the retrieval of geographic coordinates for a given city.
     * This test verifies that the {@link WeatherDataService#getCoordinates(String)} method
     * returns accurate longitude and latitude for the city of Helsinki.
     * @throws IOException if fetching coordinates fails
     */
    @Test
    public void testGetCoordinates() throws IOException {
        // Use the actual API response as coordinates won't change
        Coordinate result = weatherDataService.getCoordinates("Helsinki");
        
        assertNotNull(result, "The result should not be null.");
        assertEquals(24.9427, result.getLongitude(), 0.01, "Longitude should match the expected value.");
        assertEquals(60.1675, result.getLatitude(), 0.01, "Latitude should match the expected value.");
    }

    /**
     * Tests the retrieval of daily weather forecast data.
     * This test mocks the API responses to fetch geographic data and the daily forecast,
     * then verifies the correctness of the parsed weather data for a specific date.
     * @throws IOException if reading from resources or fetching forecast fails
     */
    @Test
    public void testGetDailyForecast() throws IOException {
        String sampleGeoLocation = new String(Files.readAllBytes(Paths.get("src/test/resources/coordinate_api_response.json")));
        String sampleDailyForecast = new String(Files.readAllBytes(Paths.get("src/test/resources/daily_api_response.json")));
        
        // Mocking fetchDataFromAPI for different API endpoint calls
        doReturn(sampleGeoLocation).when(weatherDataService).fetchDataFromAPI(contains("geo"));
        doReturn(sampleDailyForecast).when(weatherDataService).fetchDataFromAPI(contains("forecast/daily"));
        
        // Performing the actual call
        Map<Instant, DailyWeather> forecast = weatherDataService.getDailyForecast("Helsinki", "metric");

        // Assertions to verify the parsed output is correct
        assertNotNull(forecast, "Forecast data should not be null.");
        assertFalse(forecast.isEmpty(), "Forecast data should not be empty.");
        
        // Converting Unix timestamp of first day in file to Instant
        long unixTimestamp = 1713088800;
        Instant firstInstant = Instant.ofEpochSecond(unixTimestamp);
        
        // Check the values of the first DailyWeather are correct
        DailyWeather firstDay = forecast.get(firstInstant);
        
        assertEquals(6.1, firstDay.getDayTemp(), 0.01, "First day temperature "
                + "should match the expected value.");
        assertEquals(8.23, firstDay.getRainVolume(), 0.01, "First day rain volume "
                + "should match the expected value.");

        // Check that all days have a temperature
        forecast.forEach((date, weather) -> {
            assertNotNull(weather.getDayTemp(), "Temperature data should not be null.");
        });
    }

    /**
     * Tests the retrieval of hourly weather forecast data.
     * This test mocks the API responses for geographic and hourly forecast data,
     * and verifies the temperature and rain volume for the first hour of the available data.
     * @throws IOException if reading from resources or fetching forecast fails
     */
    @Test
    public void testGetHourlyForecast() throws IOException {
        String sampleGeoLocation = new String(Files.readAllBytes(Paths.get("src/test/resources/coordinate_api_response.json")));
        String sampleHourlyForecast = new String(Files.readAllBytes(Paths.get("src/test/resources/hourly_api_response.json")));
        
        // Mocking fetchDataFromAPI for different API endpoint calls
        doReturn(sampleGeoLocation).when(weatherDataService).fetchDataFromAPI(contains("geo"));
        doReturn(sampleHourlyForecast).when(weatherDataService).fetchDataFromAPI(contains("forecast/hourly"));
        
        Map<Instant, HourlyWeather> forecast = weatherDataService.getHourlyForecast("Helsinki", "metric");
        
        // Converting Unix timestamp of first day in file to Instant
        long unixTimestamp = 1713103200;
        Instant firstInstant = Instant.ofEpochSecond(unixTimestamp);
        
        // Check the values of the first DailyWeather are correct
        HourlyWeather firstHour = forecast.get(firstInstant);
        
        assertEquals(5.6, firstHour.getTemperature(), 0.01, "First hour temperature "
                + "should match the expected value.");
        assertEquals(0.25, firstHour.getRain1h(), 0.01, "First hour rain volume "
                + "should match the expected value.");
        
        // Check that every hour contains data
        forecast.forEach((date, weather) -> {
            assertNotNull(weather.getTemperature(), "Temperature data should not be null.");
            assertNotNull(weather.getWeatherIcon(), "Weather icon data should not be null.");
        });
    }
}