package fi.tuni.prog3.weatherapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


/**
 * Controller for the main application window in a JavaFX application.
 * 
 * This class is responsible for initializing the application's main window,
 * setting up the initial view, and managing transitions between different
 * views within the application, such as switching between the forecast view
 * and the weather map view. It also handles updating UI elements, such as
 * dynamic labels.
 * 
 * @author Kalle Lahtinen
 */
class MainController {
    private Stage stage;
    private Label cityTextLabel; // For dynamic text updates
    private BorderPane rootLayout; // 

    /**
     * Constructs a new MainController with a reference to the primary stage.
     * This enables the controller to manipulate the primary window of the application,
     * such as setting the scene.
     *
     * @param stage The primary stage of the JavaFX application.
     */
    public MainController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the main view of the application by setting up the initial
     * layout, including a toolbar and the default content area. The default
     * view presented is the forecast view.
     */
    public void initMainView() {
        rootLayout = new BorderPane();
        ToolBar toolBar = createToolBar();
        rootLayout.setTop(toolBar);
        
        switchToForecastView(); // Default view

        Scene scene = new Scene(rootLayout, 400, 300);
        stage.setScene(scene);
    }

    /**
     * Creates a toolbar with buttons to switch views and a label for dynamic text.
     * The toolbar is part of the application's main view and allows for navigation
     * between the forecast view and the weather map view.
     *
     * @return A configured ToolBar instance with navigation buttons and a "city" label.
     */
    private ToolBar createToolBar() {
        ToolBar toolbar = new ToolBar();
        
        Button forecastButton = new Button("Forecast");
        forecastButton.setOnAction(e -> switchToForecastView());

        Button weatherMapButton = new Button("Weather Map");
        weatherMapButton.setOnAction(e -> switchToWeatherMapView());

        cityTextLabel = new Label("Welcome");

        // Use Regions as flexible spacers
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        // Use an HBox to center the label
        HBox labelContainer = new HBox(cityTextLabel);
        labelContainer.setAlignment(Pos.CENTER);

        // Set the maximum width to Double.MAX_VALUE to allow the spacer to grow indefinitely.
        leftSpacer.setMaxWidth(Double.MAX_VALUE);
        rightSpacer.setMaxWidth(Double.MAX_VALUE);

        toolbar.getItems().addAll(forecastButton, leftSpacer, labelContainer, rightSpacer, weatherMapButton);
        
        return toolbar;
    }

    /**
     * Switches the main content area of the application to the weather forecast view.
     */
    public void switchToForecastView() {
        rootLayout.setCenter(new Label("Forecast View")); // Placeholder for actual forecast view content.
    }

    /**
     * Switches the main content area of the application to the weather map view.
     */
    public void switchToWeatherMapView() {
        rootLayout.setCenter(new Label("Weather Map View")); // Placeholder for actual weather map view content.
    }

    /**
     * Updates the text displayed on the city label within the toolbar.
     * This allows for dynamic updates based on user actions or other inputs.
     *
     * @param newText The new text to display on the city label.
     */
    public void updateCityLabel(String newText) {
        cityTextLabel.setText(newText);
    }
}