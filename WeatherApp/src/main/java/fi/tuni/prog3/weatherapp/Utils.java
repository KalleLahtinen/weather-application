package fi.tuni.prog3.weatherapp;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


/**
 * A class for the utility functions of the Weatherapp application
 */
public class Utils {

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
        autoCompletionBinding.setMinWidth(50); // Adjust the width of the suggestions popup

        return searchField;
    }
    
    /**
     * Creates and returns a custom ControlsFX ToggleSwitch for switching 
     * between metric and imperial units.
     * 
     * @return A ToggleSwitch with the labels "Metric" and "Imperial".
     */
    public static HBox createUnitToggle() {
        // Create the ToggleSwitch
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.getStyleClass().add("toggle-switch");

        // Create labels for Metric and Imperial
        Label labelLeft = new Label("Metric");
        Label labelRight = new Label("Imperial");

        // Styling to make the labels appear as part of the switch
        labelLeft.getStyleClass().add("switch-label-left");
        labelRight.getStyleClass().add("switch-label-right");

        // Align labels and switch in a horizontal box
        HBox toggleBox = new HBox(labelLeft, toggleSwitch, labelRight);        
        toggleBox.setAlignment(Pos.CENTER);  // Center-align elements vertically
        
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Switched to Imperial");
            } else {
                System.out.println("Switched to Metric");
            }
        });
        
        return toggleBox;
    }
}