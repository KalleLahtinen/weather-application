package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.stage.Stage;

/*
    ChatGPT 3.5 was heavily utilized in brainstorming the best class division and
    architecture for the project, aiming to make educated design decisions
    with the use of MVC architecture and property binding with SPOT.
*/

/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application {

    /**
     * Initializes and shows the primary stage of this JavaFX application.
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Application");

        MainViewBuilder builder = new MainViewBuilder(primaryStage);
        builder.initMainView();
        
        primaryStage.show();
    }

    /**
     * The main entry point for this JavaFX application.
     * @param args the command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}