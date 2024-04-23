package fi.tuni.prog3.weatherapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * A builder for the main application window in the WeatherApp application.
 * 
 * This class is responsible for initializing the application's main window,
 * setting up the initial view, and managing transitions between different
 * views within the application, such as switching between the forecast view
 * and the weather map view. It also handles updating UI elements, such as
 * dynamic labels.
 * 
 * @author Kalle Lahtinen
 */
public class MainViewBuilder {
    private final Stage stage;
    private final ReadAndWriteToFile fileHandler = new ReadAndWriteToFile();
    public ApplicationStateManager appState;
    public final ViewController viewController;
    public final MeasurementSystem measurementSystem;
    private Label cityTextLabel; // For dynamic text updates
    private BorderPane rootLayout; // The main layout for the elements
    public StackPane viewContainer; // This will hold the views to enable transition effects
    public FavouriteStarToggle starToggle;

    /**
     * Constructs a new MainController with a reference to the primary stage.
     * This enables the controller to manipulate the primary window of the application,
     * such as setting the scene.
     *
     * @param stage The primary stage of the JavaFX application.
     */
    public MainViewBuilder(Stage stage) {
        this.stage = stage;

        appState = fileHandler.readFromFile();
        measurementSystem = new MeasurementSystem(appState.getUnits());
        viewController = new ViewController(this, measurementSystem, appState);
        starToggle = new FavouriteStarToggle(appState);
        
        // Save the appState object to file when application is closed
        stage.setOnCloseRequest(event -> {
            fileHandler.writeToFile(appState);
        });
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
        viewContainer = viewController.initViewContainer();
        
        rootLayout.setCenter(viewContainer);

        // Create a view selector at bottom of layout
        createBottomTabBar();

        Scene scene = new Scene(rootLayout);
        // Link CSS file for formatting
        scene.getStylesheets().add(getClass().getResource("/fi/tuni/prog3/weatherapp/styles.css").toExternalForm());
        stage.setWidth(616);
        stage.setHeight(685);
        stage.setResizable(false);
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
        
        HBox unitToggle = Utils.createUnitToggle(this);

        TextField searchBar = Utils.createSearchBarWithSuggestions();
        searchBar.setOnAction(e -> viewController.searchHandler(searchBar.getText()));

        cityTextLabel = new Label(appState.getCurrentCity());
        cityTextLabel.setId("cityTextLabel");
        
        ToggleButton favouriteStar = starToggle.getToggleButton();

        // Use Regions as flexible spacers
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.SOMETIMES);
        HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

        // Set the maximum width to Double.MAX_VALUE to allow the spacer to grow indefinitely.
        leftSpacer.setMaxWidth(Double.MAX_VALUE);
        rightSpacer.setMaxWidth(Double.MAX_VALUE);

        toolbar.getItems().addAll(unitToggle, leftSpacer, cityTextLabel, favouriteStar, rightSpacer, searchBar);
        
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
        bottomToolbar.setPadding(new Insets(15, 0, 15, 0)); // Top, Right, Bottom, Left
        bottomToolbar.setAlignment(Pos.CENTER);
        
        Button btnView1, btnView2, btnView3; // Buttons to switch views

        // Initialize buttons
        btnView1 = new Button("View 1");
        btnView2 = new Button("View 2");
        btnView3 = new Button("View 3");

        // Button actions
        btnView1.setOnAction(e -> viewController.switchView(ViewController.View.FORECAST));
        btnView2.setOnAction(e -> viewController.switchView(ViewController.View.WEATHERMAP));
        btnView3.setOnAction(e -> viewController.switchView(ViewController.View.HISTORY));

        bottomToolbar.getChildren().addAll(btnView1, btnView2, btnView3);
        rootLayout.setBottom(bottomToolbar);
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