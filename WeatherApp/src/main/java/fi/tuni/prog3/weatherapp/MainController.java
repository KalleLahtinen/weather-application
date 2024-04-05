package fi.tuni.prog3.weatherapp;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private BorderPane rootLayout; // The main layout for the elements
    private StackPane viewContainer; // This will hold the views to enable transition effects

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
     * Initializes the main view of the application.
     *
     * This method sets up the primary layout structure, including a toolbar at the top, 
     * a container for switching between different views in the center, and a view selector 
     * tab bar at the bottom. The main view container is designed to hold multiple views, 
     * but initially displays only the first view to the user.
     */
    public void initMainView() {
        rootLayout = new BorderPane();
        // Create a toolbar at top of layout
        createToolbar();

        // Initialize the views in the application
        viewContainer = new StackPane();
        Node view1Content = new Label("View 1 Content"); // Replace with actual view
        Node view2Content = new Label("View 2 Content"); // Replace with actual view
        Node view3Content = new Label("View 3 Content"); // Replace with actual view

        // Only the first view is initially visible
        viewContainer.getChildren().addAll(view1Content, view2Content, view3Content);
        view2Content.setVisible(false);
        view3Content.setVisible(false);
        
        rootLayout.setCenter(viewContainer);

        // Create a view selector at bottom of layout
        createBottomTabBar();

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
    private void createToolbar() {
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
        
        rootLayout.setTop(toolbar);
    }
    
    /**
    * Creates and initializes the bottom tab bar for view selection.
    * 
    * This method sets up a horizontal box with buttons to switch between different application views.
    * Each button is configured with an action to switch views when clicked. The bottom tab bar is 
    * added to the bottom of the root layout, and the first view is selected by default.
    */
    private void createBottomTabBar() {
        HBox bottomToolbar = new HBox(10); // Spacing between buttons
        bottomToolbar.setAlignment(Pos.CENTER);
        
        Button btnView1, btnView2, btnView3; // Buttons to switch views

        // Initialize buttons
        btnView1 = new Button("View 1");
        btnView2 = new Button("View 2");
        btnView3 = new Button("View 3");

        // Button actions
        btnView1.setOnAction(e -> switchToForecastView());
        btnView2.setOnAction(e -> switchToWeatherMapView());
        btnView3.setOnAction(e -> switchToForecastView());

        bottomToolbar.getChildren().addAll(btnView1, btnView2, btnView3);
        rootLayout.setBottom(bottomToolbar);

        // Initially select the first view
        switchToForecastView();
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