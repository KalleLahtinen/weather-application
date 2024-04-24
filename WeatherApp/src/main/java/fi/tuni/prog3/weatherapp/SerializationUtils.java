package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.lang.reflect.Type;

/**
 * An utility class that contains custom (de)serializers for JavaFX properties.
 * 
 * @author Kalle Lahtinen
 */
public class SerializationUtils {

    /**
     * Custom JsonSerializer for {@code ListProperty<String>}.
     */
    public static class ListPropertySerializer implements JsonSerializer<ListProperty<String>> {
        @Override
        public JsonElement serialize(ListProperty<String> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray json = new JsonArray();
            for (String item : src.get()) {
                json.add(item);
            }
            return json;
        }
    }

    /**
     * Custom JsonDeserializer for {@code ListProperty<String>}.
     */
    public static class ListPropertyDeserializer implements JsonDeserializer<ListProperty<String>> {
        @Override
        public ListProperty<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ListProperty<String> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                listProperty.add(element.getAsString());
            }
            return listProperty;
        }
    }
}