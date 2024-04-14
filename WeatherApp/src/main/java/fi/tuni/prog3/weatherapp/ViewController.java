
package fi.tuni.prog3.weatherapp;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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
        NOVIEW;

        public static View fromIndex(int index) {
            for (View v : View.values()) {
                if (v.ordinal() == index) {
                    return v;
                }
            }
            throw new IllegalArgumentException("No view for index: " + index);
        }
    }
    
    private MainViewBuilder mainViewBuilder;
    private WeatherDataService weatherDataService;
    private StackPane viewContainer; // Holds the views
    private View currentView = View.FORECAST; // Default to the FORECAST view being visible
    private String currentCity;

    public ViewController(MainViewBuilder builder) {
        mainViewBuilder = builder;
        weatherDataService = new WeatherDataService();
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
        Node view1Content = new Label("View 1 Content"); // Replace with actual view
        Node view2Content = new Label("View 2 Content"); // Replace with actual view
        Node view3Content = new Label("View 3 Content"); // Replace with actual view
        
        viewContainer.getChildren().addAll(view1Content, view2Content, view3Content);
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
        }
    }
}
