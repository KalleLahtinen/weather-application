package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import java.lang.reflect.Type;

/*
    ChatGPT 3.5 and Gemini were heavily utilized in the creation and documentation 
    of this class to learn about the need for custom serialization for JavaFX properties,
    generate and iterate these methods and then troubleshoot the problems caused 
    by restricted access between java packages. Oh, and Javadoc comments.
*/

/**
 * An utility class that contains custom (de)serializers for JavaFX properties.
 * 
 * @author Kalle Lahtinen
 */
public class SerializationUtils {

    /**
     * Default constructor for SerializationUtils class
     */
    public SerializationUtils() {
        // Default constuctor implementation
    }
    
    /**
    * Custom JsonSerializer for {@code ListProperty<String>}.
    * This serializer converts a ListProperty containing strings into a JSON array.
    */
    public static class ListPropertySerializer implements JsonSerializer<ListProperty<String>> {
        
        /**
         * Default constructor for ListPropertySerializer class
         */
        public ListPropertySerializer() {}
        
        /**
        * Serializes the source {@code ListProperty<String>} to a JsonArray.
        * 
        * @param src The source ListProperty containing strings.
        * @param typeOfSrc The specific genericized type of source.
        * @param context Context for serialization that is passed to a custom serializer during invocation of its serialize method.
        * @return A JsonArray containing all the strings from the ListProperty.
        */
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
    * This deserializer converts a JsonArray into a ListProperty containing strings.
    */
    public static class ListPropertyDeserializer implements JsonDeserializer<ListProperty<String>> {
        /**
         * Default constructor for ListPropertyDeserializer class
         */
        public ListPropertyDeserializer() {}
        
        /**
        * @param json The JSON being deserialized, expected to be a JsonArray.
        * @param typeOfT The type of the Object to deserialize to.
        * @param context Deserialization context. This provides a way for custom deserializers to access the Gson instance during deserialization.
        * @return A new ListProperty initialized with strings from the JsonArray.
        * @throws JsonParseException if the JSON is not in the expected format of an array.
        */
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
    
    /**
    * Custom JsonSerializer for {@code StringProperty}.
    * This serializer converts a StringProperty into a JSON element.
    */
   public static class StringPropertySerializer implements JsonSerializer<StringProperty> {
        /**
         * Default constructor for StringPropertySerializer class
         */
        public StringPropertySerializer() {}
        
       /**
        * Serializes the source StringProperty to a JsonElement.
        * 
        * @param src The source StringProperty.
        * @param typeOfSrc The specific genericized type of source.
        * @param context Context for serialization that is passed to a custom serializer during invocation of its serialize method.
        * @return A JsonPrimitive containing the string value held by the StringProperty.
        */
        @Override
        public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.getValue());
        }
   }
   
   /**
    * Custom JsonDeserializer for {@code StringProperty}.
    * This deserializer converts a JsonElement into a StringProperty.
    */
   public static class StringPropertyDeserializer implements JsonDeserializer<StringProperty> {
        /**
         * Default constructor for StringPropertyDeserializer class
         */
        public StringPropertyDeserializer() {}
       
       
       /**
        * Deserializes the specified JsonElement into a StringProperty.
        * 
        * @param json The JSON being deserialized.
        * @param typeOfT The type of the Object to deserialize to.
        * @param context Deserialization context. This provides a way for custom deserializers to access the Gson instance during deserialization.
        * @return A new StringProperty initialized with the string value from the JsonElement.
        * @throws JsonParseException if the JSON is not in the expected format of a simple string.
        */
        @Override
        public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new SimpleStringProperty(json.getAsJsonPrimitive().getAsString());
        }
   }
}