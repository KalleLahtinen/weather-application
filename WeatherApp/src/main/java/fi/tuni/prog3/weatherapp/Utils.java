package fi.tuni.prog3.weatherapp;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


/**
 * A class for the utility functions of the Weatherapp application.
 */
public class Utils {
    
    /**
     * Default constructor for Utils class
     */
    public Utils() {
        // Default constuctor implementation
    }

    /**
     * Creates and returns a TextField with autocomplete suggestions and a clear button.
     * 
     * @return A configured TextField with autocomplete and clear functionalities.
     */
    public static TextField createSearchBarWithSuggestions() {
        var suggestions = FXCollections.observableArrayList(
                "Tampere",
                "Helsinki",
                "Turku",
                "Amsterdam",
                "Paris",
                "New York",
                "Tokyo",
                "London",
                "Sydney",
                "Moscow",
                "Rio de Janeiro",
                "Beijing",
                "Cape Town");

        // Create a clearable TextField
        TextField searchField = TextFields.createClearableTextField();
        searchField.setPromptText("Search for city");

        // Bind auto-completion to the searchField
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(searchField, suggestions);
        autoCompletionBinding.setMinWidth(50);
        
        // Turn text color back to normal on edit after possible invalid search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchField.setStyle("-fx-text-fill: black;");
        });

        return searchField;
    }
    
    /**
     * Creates and returns a custom ControlsFX ToggleSwitch for switching 
     * between metric and imperial units.
     * 
     * @param mwBuilder A reference to the MainViewBuilder class for access 
     *                  to currently used MeasurementSystem object.
     * @return A ToggleSwitch with the labels "Metric" and "Imperial".
     */
    public static HBox createUnitToggle(MainViewBuilder mwBuilder) {
        // Create the ToggleSwitch
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.getStyleClass().add("toggle-switch");
        
        if (mwBuilder.appState.units.equals("imperial")) {
            toggleSwitch.setSelected(true);
        } else {
            toggleSwitch.setSelected(false);
        }

        // Create labels for Metric and Imperial
        Label labelLeft = new Label("Metric");
        Label labelRight = new Label("Imperial");

        // Styling to make the labels appear as part of the switch
        labelLeft.getStyleClass().add("switch-label-left");
        labelRight.getStyleClass().add("switch-label-right");

        // Align labels and switch in a horizontal box
        HBox toggleBox = new HBox(labelLeft, toggleSwitch, labelRight);        
        toggleBox.setAlignment(Pos.CENTER);
        
        // Toggle between selectable measurement systems
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                mwBuilder.measurementSystem.changeSystem("imperial");
                mwBuilder.appState.setUnits("imperial");
                mwBuilder.viewController.updateWeatherData();
            } else {
                mwBuilder.measurementSystem.changeSystem("metric");
                mwBuilder.appState.setUnits("metric");
                mwBuilder.viewController.updateWeatherData();
            }
        });
        
        return toggleBox;
    }
}