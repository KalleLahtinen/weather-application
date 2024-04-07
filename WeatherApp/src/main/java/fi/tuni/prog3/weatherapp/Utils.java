package fi.tuni.prog3.weatherapp;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
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
        var suggestions = FXCollections.observableArrayList("Apple", "Banana", "Cherry", "Date");

        // Create a clearable TextField
        TextField searchField = TextFields.createClearableTextField();
        searchField.setPromptText("Search for city");

        // Bind auto-completion to the searchField
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(searchField, suggestions);
        autoCompletionBinding.setMinWidth(50); // Adjust the width of the suggestions popup

        return searchField;
    }
}