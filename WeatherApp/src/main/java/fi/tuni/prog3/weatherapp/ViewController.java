package fi.tuni.prog3.weatherapp;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
    ChatGPT 3.5 was heavily utilized in the creation and documentation of this class 
    to brainstorm possible ways of implementation, refactor code and create Javadoc comments.
*/

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
        CITY_LISTS,
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
    private StackPane viewContainer; // Holds the views
    private View currentView = View.FORECAST; // Default to the FORECAST view being visible
    
    private final MainViewBuilder mainViewBuilder;
    private final ForecastViewController forecastView;
    
    private final WeatherDataService weatherDataService = new WeatherDataService();
    private final MeasurementSystem measurementSystem;
    private final ApplicationStateManager appState;
    private final CityListManager cityListManager;

    /**
     * Constructs a ViewController with the specified main view builder.
     * Initializes the forecast view with data for the default city and unit.
     * 
     * @param mwBuilder the main view builder used for creating and managing UI components.
     * @param measurementSystem the MeasurementSystem object keeping track of 
     *        current system of measurement and measurement unit properties.
     * @param appState The ApplicationStateManager object containing session data.
     */
    public ViewController(MainViewBuilder mwBuilder, MeasurementSystem measurementSystem, 
                          ApplicationStateManager appState) {
        this.mainViewBuilder = mwBuilder;
        this.measurementSystem = measurementSystem;
        this.appState = appState;
        this.cityListManager = new CityListManager(mwBuilder, appState);
                
        forecastView = new ForecastViewController(measurementSystem,
                weatherDataService.getDailyForecast(appState.getCurrentCity(), appState.getUnits()),
                weatherDataService.getHourlyForecast(appState.getCurrentCity(), appState.getUnits()));
        
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

        viewContainer = new StackPane();
        VBox forecast = this.forecastView.getView();
        HBox cityLists = this.cityListManager.getView();
        
        viewContainer.getChildren().addAll(forecast, cityLists);
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
     * Changes the current city to {@code city} and updates application.
     * 
     * @param city the city to be set as current.
     */
    public void changeCurrentCity(String city) {
        appState.setCurrentCity(city);
        updateWeatherData();
        mainViewBuilder.starToggle.setFavorited(appState.isCurrentCityFavourited());
    }
    
    /**
     * Updates all weather views with a new API call using current selections.
     */
    public void updateWeatherData() {
        forecastView.updateHourlyWeathers(weatherDataService.getHourlyForecast(
                appState.getCurrentCity(), appState.getUnits()));
        forecastView.updateDailyWeathers(weatherDataService.getDailyForecast(
                appState.getCurrentCity(), appState.getUnits()));
    }
    
    /**
     * Handles the search operation initiated by the user. Updates all views with
     * the new city's weather data if the city is found with this name in some language. 
     * If city is not found, the search field is colored red.
     *
     * @param query the search query entered by the user, typically a city name.
     */
    public void searchHandler(String query) {
        String city = weatherDataService.getCity(query);
        if (city != null) {
            changeCurrentCity(city);
            appState.addCityToHistory(city);
        } else {
            mainViewBuilder.searchBar.setStyle("-fx-text-fill: red;");
        }
    }
}