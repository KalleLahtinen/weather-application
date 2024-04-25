package fi.tuni.prog3.weatherapp;

import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/*
    ChatGPT 3.5 was heavily utilized in the creation and documentation of this class 
    after using it to brainstorm possible ways of implementation.
*/

/**
 * This class encapsulates a toggle button for favoriting functionality.
 * It uses SVG paths to display a star that can be toggled between filled and empty states.
 * 
 * @author Kalle Lahtinen
 */
public class FavouriteStarToggle {
    private final ToggleButton toggleButton = new ToggleButton();
    private final SVGPath starEmpty = new SVGPath();
    private final SVGPath starFilled = new SVGPath();
    private final ApplicationStateManager appState;

    /**
     * Constructs a FavoriteStarToggle with the specified MainViewBuilder.
     * @param appState The ApplicationStateManager object containing session data.
     */
    public FavouriteStarToggle(ApplicationStateManager appState) {
        this.appState = appState;
        initialize();
    }

    /**
     * Initializes the toggle button with default settings and behavior.
     */
    private void initialize() {
        // Styles
        toggleButton.setStyle("-fx-background-color: transparent; "
                + "-fx-padding: 5, 5, 5, 5; -fx-cursor: hand;");

        // Define empty star
        starEmpty.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-"
                           + ".61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starEmpty.setFill(Color.TRANSPARENT);
        starEmpty.setStroke(Color.BLACK);

        // Define filled star
        starFilled.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-"
                            + ".61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starFilled.setFill(Color.YELLOW);
        starFilled.setStroke(Color.BLACK);

        toggleButton.setGraphic(starEmpty);
        toggleButton.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                toggleButton.setGraphic(starFilled);
                appState.addFavoriteCity(appState.getCurrentCity());
            } else {
                toggleButton.setGraphic(starEmpty);
                appState.removeFavoriteCity(appState.getCurrentCity());
            }
        });
    }

    /**
     * Gets the underlying ToggleButton component.
     * @return the ToggleButton used in this FavoriteStarToggle.
     */
    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    /**
     * Sets the favorited state of the toggle button.
     * @param favored true to set the toggle button to the favorited (filled star) state, false otherwise.
     */
    public void setFavorited(boolean favored) {
        toggleButton.setSelected(favored);
    }

    /**
     * Checks if the toggle button is currently set to the favorited state.
     * @return true if the toggle button is in the favorited state, false otherwise.
     */
    public boolean isFavorited() {
        return toggleButton.isSelected();
    }
}
