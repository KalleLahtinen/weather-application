package fi.tuni.prog3.weatherapp;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * Manages the views and the switching mechanism for different weather-related views
 * in the application.
 * 
 * @author Kalle Lahtinen
 */
public final class ViewController {
    /**
     * Enum representing the different types of views available in the application.
     */
    public enum View {
        FORECAST,
        WEATHERMAP,
        HISTORY,
        FAVOURITES,
        NO_VIEW;

        /**
         * Retrieves a View enum based on its ordinal index.
         *
         * @param index the ordinal index of the View enum.
         * @return the View enum corresponding to the given index.
         * @throws IllegalArgumentException if no View corresponds to the given index.
         */
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
    private String currentUnits;
    
    private final ForecastViewController forecastView;

    /**
     * Constructs a ViewController with the specified main view builder.
     * Initializes the forecast view with data for the default city and unit.
     * 
     * @param builder the main view builder used for creating and managing UI components.
     * @param measurementSystem the MeasurementSystem object keeping track of 
     *        current system of measurement and measurement unit properties.
     */
    public ViewController(MainViewBuilder builder, MeasurementSystem measurementSystem) {
        this.mainViewBuilder = builder;
        weatherDataService = new WeatherDataService();
        currentCity = "Helsinki";      // Get this from history
        currentUnits = "metric";       // Get this from history
        forecastView = new ForecastViewController(measurementSystem,
                weatherDataService.getDailyForecast(currentCity, currentUnits),
                weatherDataService.getHourlyForecast(currentCity, currentUnits));
        initViewContainer();
    }

    /**
     * Initializes or reinitializes the container holding the different views.
     * If the viewContainer already exists, it preserves the currently visible view.
     *
     * @return A StackPane containing all the views managed by this controller.
     */
    public StackPane initViewContainer() {
        // Before recreating the views, remember the visible one
        if (viewContainer != null) {
            rememberCurrentView();
        }

        // Create the views
        viewContainer = new StackPane();
        VBox forecast = this.forecastView.getView();
        Node view2Content = new Label("View 2 Content"); // Replace with actual view
        Node view3Content = new Label("View 3 Content"); // Replace with actual view
        
        viewContainer.getChildren().addAll(forecast, view2Content, view3Content);
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
     * Switches the visible view in the application to the specified new view.
     * 
     * @param newView the view to switch to.
     */
    public void switchView(View newView) {
        // Make all views invisible first
        viewContainer.getChildren().forEach(node -> node.setVisible(false));

        // Set the newly selected view to visible
        viewContainer.getChildren().get(newView.ordinal()).setVisible(true);

        // Remember the current view
        currentView = newView;
    }
    
    /**
     * Handles the search operation initiated by the user. Updates all views with
     * the new city's weather data if the city exists.
     *
     * @param query the search query entered by the user, typically a city name.
     */
    public void searchHandler(String query) {
        String city = weatherDataService.getCity(query);
        if (city != null) {
            currentCity = city;
            mainViewBuilder.updateCityLabel(currentCity);
            
            forecastView.updateHourlyWeathers(weatherDataService.getHourlyForecast(currentCity, currentUnits));
            forecastView.updateDailyWeathers(weatherDataService.getDailyForecast(currentCity, currentUnits));
        }
    }
}