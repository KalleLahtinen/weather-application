package fi.tuni.prog3.weatherapp;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/*
    ChatGPT 3.5 was heavily utilized in this class to brainstorm possible 
    courses of action, best practises, JavaFX features and then iterating 
    code to make work more efficient. This made it possible to focus on 
    the bigger picture and architecture, with cleaner class division and code.
 */

/**
 * Manages the display and interaction of city lists, including search history and favourite cities.
 * 
 * @author Kalle Lahtinen
 */
public class CityListManager {
    private final MainViewBuilder mwBuilder;
    private final ApplicationStateManager appState;
    private ListView<String> historyListView;
    private ListView<String> favouriteListView;
    private HBox cityListView;

    /**
     * Constructs a CityListManager for managing city lists.
     *
     * @param mwBuilder the main view builder used for creating and managing UI components.
     * @param appState The application state manager, holding the data for the city lists.
     */
    public CityListManager(MainViewBuilder mwBuilder, ApplicationStateManager appState) {
        this.mwBuilder = mwBuilder;
        this.appState = appState;
        createCityListView();
    }

    /**
     * Initializes and arranges the city list views within a horizontal box layout.
     * List elements are bound to the data properties in ApplicationStateManager object.
     */
    private void createCityListView() {
        historyListView = new ListView<>();
        historyListView.getStyleClass().add("list-row");
        
        favouriteListView = new ListView<>();
        favouriteListView.getStyleClass().add("list-row");

        historyListView.itemsProperty().bind(appState.historyProperty());
        favouriteListView.itemsProperty().bind(appState.favouritesProperty());
        
        setupClickHandler(historyListView, favouriteListView, city -> {
            mwBuilder.viewController.changeCurrentCity(city);
        });
        setupClickHandler(favouriteListView, historyListView, city -> {
            mwBuilder.viewController.changeCurrentCity(city);
        });

        VBox historyBox = createListBox(historyListView, "Search History");
        VBox favouriteBox = createListBoxWithDelete(favouriteListView, "Favourited Cities");

        this.cityListView = new HBox(historyBox, favouriteBox);
        cityListView.setAlignment(Pos.CENTER);
        cityListView.setPadding(new Insets(40, 15, 15, 15));
        cityListView.setSpacing(20);
    }

    /**
     * Creates a labeled list box for displaying cities.
     *
     * @param listView The ListView to be included in the box.
     * @param title The title label for the list box.
     * @return A VBox containing a label and a ListView.
     */
    private VBox createListBox(ListView<String> listView, String title) {
        Label label = new Label(title);
        label.getStyleClass().add("list-title");
        VBox box = new VBox(10, label, listView);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates a labeled list box with a delete button for each city.
     *
     * @param listView The ListView for displaying favourite cities.
     * @param title The title of the list.
     * @return A VBox containing a label and a ListView with deletion capabilities.
     */
    private VBox createListBoxWithDelete(ListView<String> listView, String title) {
        listView.setCellFactory(param -> new ListCell<String>() {
            Label label = new Label();
            Region spacer = new Region();
            Button removeButton = new Button("X");     
            HBox hbox = new HBox(label, spacer, removeButton);

            {
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hbox.setAlignment(Pos.CENTER);
                spacer.setMaxWidth(Double.MAX_VALUE);
                
                removeButton.setId("list-remove-btn");
                removeButton.getStyleClass().add("list-text");
                removeButton.setOnAction(event -> {
                    appState.removeFavoriteCity(getItem());
                    mwBuilder.starToggle.setFavorited(appState.isCurrentCityFavourited());
                });
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setPrefHeight(35);
                } else {
                    ((Label) hbox.getChildren().get(0)).setText(item);
                    setGraphic(hbox);
                    setPrefHeight(35);
                }
            }
        });

        return createListBox(listView, title);
    }
    
    private void setupClickHandler(ListView<String> listView, ListView<String> otherListView, Consumer<String> onItemClick) {
        listView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    onItemClick.accept(selectedItem);
                }
                otherListView.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Returns the main view containing both the history and favourite city lists.
     *
     * @return An HBox containing all UI elements managed by this class.
     */
    public HBox getView() {
        return cityListView;
    }
}