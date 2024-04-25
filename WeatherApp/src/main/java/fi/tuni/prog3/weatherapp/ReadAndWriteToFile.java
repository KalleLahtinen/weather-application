package fi.tuni.prog3.weatherapp;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for reading and writing favorite cities and history to/from JSON files.
 * 
 * @author Roope Kärkkäinen & Kalle Lahtinen
 */
public class ReadAndWriteToFile implements iReadAndWriteToFile {
    /**
     * A GsonBuilder class that can (de)serialize JavaFX properties. 
     * An exclusion strategy is implemented to prevent Gson from trying to 
     * access JavaFX fields in cannot, possible due to the Java 9 Module System, 
     * which restricts access between modules by default.
     */
    public class GsonConfiguration {
        public static Gson create() {
            ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                  return clazz.getPackageName().startsWith("com.sun.javafx");
                }

                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                  return false; // Don't exclude any specific fields
                }
            };
            return new GsonBuilder()
                .registerTypeAdapter(new TypeToken<ListProperty<String>>(){}.getType(), new SerializationUtils.ListPropertySerializer())
                .registerTypeAdapter(new TypeToken<ListProperty<String>>(){}.getType(), new SerializationUtils.ListPropertyDeserializer())
                .registerTypeAdapter(new TypeToken<StringProperty>(){}.getType(), new SerializationUtils.StringPropertySerializer())
                .registerTypeAdapter(new TypeToken<StringProperty>(){}.getType(), new SerializationUtils.StringPropertyDeserializer())
                .setExclusionStrategies(exclusionStrategy)
                .create();
        }
    }
    
    /**
     * Method to read application state from a JSON file. Uses custom GsonBuilder 
     * that can convert JavaFX properties to regular types and vice versa.
     * 
     * @return A ApplicationStateManager object created based on read file, 
     *         a fresh instance if file could not be read.
     */
    @Override
    public ApplicationStateManager readFromFile() {
        Gson gson = GsonConfiguration.create();
        
        ApplicationStateManager appState = null;

        try (Reader reader = new FileReader("appstate.json")) {
            appState = gson.fromJson(reader, ApplicationStateManager.class);
        } catch (IOException e) {
            appState = new ApplicationStateManager();
        }
        
        if (appState == null) { 
            appState = new ApplicationStateManager();
        }
        return appState;
    }
    
    /**
     * Method to write application state to a JSON file. Uses custom GsonBuilder 
     * that can convert JavaFX properties to regular types and vice versa.
     */
    @Override
    public void writeToFile(ApplicationStateManager appState) {
        Gson gson = GsonConfiguration.create();
         // Convert ApplicationStateManager object to JSON string
        String json = gson.toJson(appState);

        // Write JSON string to file
        try (FileWriter writer = new FileWriter("appstate.json")) {
            writer.write(json);
        } catch (IOException e) {
            String errorMessage = "Error writing to file: appstate.json";
            LoggingInformation.logError(errorMessage, e);
        }
    }
}