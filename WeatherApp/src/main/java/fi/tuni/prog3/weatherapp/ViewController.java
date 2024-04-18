package fi.tuni.prog3.weatherapp;

import java.time.Instant;
import java.util.TreeMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * 
 * @author Kalle Lahtinen
 */
public class ViewController {
    public enum View {
        FORECAST,
        WEATHERMAP,
        HISTORY,
        FAVOURITES,
        NO_VIEW;

        public static View fromIndex(int index) {
            for (View v : View.values()) {
                if (v.ordinal() == index) {
                    return v;
                }
            }
            throw new IllegalArgumentException("No view for index: " + index);
        }
    }
    
    private final MainViewBuilder mainViewBuilder;
    private final WeatherDataService weatherDataService;
    private StackPane viewContainer; // Holds the views
    private View currentView = View.FORECAST; // Default to the FORECAST view being visible
    private String currentCity;
    private String units;

    public ViewController(MainViewBuilder builder) {
        mainViewBuilder = builder;
        weatherDataService = new WeatherDataService();
        units = "metric";
    }

    /**
     * Initializes or reinitializes the view container with views.
     * @return a stackPane containing the created views
     */
    public StackPane initViewContainer() {
        // Before recreating the views, remember the visible one
        if (viewContainer != null) {
            rememberCurrentView();
        }

        // Create the views
        viewContainer = new StackPane();
        VBox forecastView = initForecastView();
        Node view2Content = new Label("View 2 Content"); // Replace with actual view
        Node view3Content = new Label("View 3 Content"); // Replace with actual view
        
        viewContainer.getChildren().addAll(forecastView, view2Content, view3Content);
        switchView(currentView);
        
        return viewContainer;
    }

    /**
     * Remembers the index of the currently visible view.
     */
    private void rememberCurrentView() {
        for (int i = 0; i < viewContainer.getChildren().size(); i++) {
            if (viewContainer.getChildren().get(i).isVisible()) {
                currentView = View.fromIndex(i);
                break; // Exit once the visible view is found
            }
        }
    }
    
    /**
     * 
     * @param newView 
     */
    public void switchView(View newView) {
        // Make all views invisible first
        viewContainer.getChildren().forEach(node -> node.setVisible(false));

        // Set the newly selected view to visible
        viewContainer.getChildren().get(newView.ordinal()).setVisible(true);

        // Remember the current view
        currentView = newView;
    }
    
    public void searchHandler(String query) {
        String cityName = weatherDataService.getCity(query);
        if (cityName != null) {
            currentCity = cityName;
            mainViewBuilder.updateCityLabel(currentCity);
            mainViewBuilder.viewContainer = initViewContainer();
        }
    }
    
    public VBox initForecastView() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);  // Set space between elements
        
        VBox currentWeather = createCurrentWeatherSection();
        vbox.getChildren().add(currentWeather);
        
        return vbox;
    }
    
    public VBox createCurrentWeatherSection() {
        TreeMap<Instant, DailyWeather> dailyWeathers = 
                new TreeMap<>(weatherDataService.getDailyForecast(currentCity, units));
        DailyWeather currentWeather = dailyWeathers.firstEntry().getValue();
        
        VBox vbox = new VBox();
        vbox.setSpacing(10);  // Set space between elements
        vbox.setAlignment(Pos.CENTER);  // Center align all elements in VBox

        // Day Temperature
        Text dayTemperatureText = new Text(currentWeather.getDayTemp() + "°C");
        dayTemperatureText.getStyleClass().add("bold-text");
        vbox.getChildren().add(dayTemperatureText);

        // Feels Like Temperature
        Text feelsLikeText = new Text("Feels like: " + currentWeather.getDayFeelsLike() + "°C");
        feelsLikeText.getStyleClass().add("normal-text");

        Text feelsLikeTemp = new Text("" + currentWeather.getDayFeelsLike());
        feelsLikeText.getStyleClass().add("bold-text");
        
        Text Text = new Text("Feels like: " + currentWeather.getDayFeelsLike() + "°C");
        feelsLikeText.getStyleClass().add("normal-text");
        
        vbox.getChildren().add(feelsLikeText);

        // HBox for Air Quality, Rain Amount, and Wind Speed
        HBox hbox = new HBox();
        hbox.setSpacing(15);  // Set space between elements in the HBox
        hbox.setAlignment(Pos.CENTER);  // Center align all elements in HBox

        Text airQualityText = new Text("Air Quality: ???"); // Placeholder for actual data
        Text rainAmountText = new Text("Rain: " + currentWeather.getRainVolume() + "mm");
        Text windSpeedText = new Text("Wind Speed: " + currentWeather.getWindSpeed() + "km/h");

        hbox.getChildren().addAll(airQualityText, rainAmountText, windSpeedText);
        vbox.getChildren().add(hbox);

        return vbox;
    }
}
