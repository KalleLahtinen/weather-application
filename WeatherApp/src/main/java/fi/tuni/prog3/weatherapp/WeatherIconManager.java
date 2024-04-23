package fi.tuni.prog3.weatherapp;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.text.Font;

/**
 * Manages weather icons based on the weather condition codes provided by the OpenWeatherMap API.
 * This class maps weather condition codes to their corresponding icon codes, which are Unicode 
 * characters representing weather icons from a custom font.
 * 
 * @author Kalle Lahtinen
 */
public class WeatherIconManager {
    // Specify the location of the icon font file
    private static final String FONT_PATH = 
            "/fi/tuni/prog3/weatherapp/weathericons-regular-webfont.ttf";

    static {
        // Load the font only once when the class is loaded
        Font.loadFont(WeatherIconManager.class.getResourceAsStream(FONT_PATH), 40);
    }

    /**
     * Static dictionary to hold mappings from weather condition codes 
     * to their respective icon Unicode characters.
     */
    private static final Map<Integer, String> ICON_CODE_MAP = new HashMap<>();

    // Initialize ICON_CODE_MAP with weather condition code to icon mappings
    static {
        // Thunderstorm
        ICON_CODE_MAP.put(200, "\uf01e");  // thunderstorm with light rain
        ICON_CODE_MAP.put(201, "\uf01e");  // thunderstorm with rain
        ICON_CODE_MAP.put(202, "\uf01e");  // thunderstorm with heavy rain
        ICON_CODE_MAP.put(210, "\uf016");  // light thunderstorm
        ICON_CODE_MAP.put(211, "\uf016");  // thunderstorm
        ICON_CODE_MAP.put(212, "\uf016");  // heavy thunderstorm
        ICON_CODE_MAP.put(221, "\uf016");  // ragged thunderstorm
        ICON_CODE_MAP.put(230, "\uf01e");  // thunderstorm with light drizzle
        ICON_CODE_MAP.put(231, "\uf01e");  // thunderstorm with drizzle
        ICON_CODE_MAP.put(232, "\uf01e");  // thunderstorm with heavy drizzle
        // Drizzle
        ICON_CODE_MAP.put(300, "\uf01c");  // light intensity drizzle
        ICON_CODE_MAP.put(301, "\uf01c");  // drizzle
        ICON_CODE_MAP.put(302, "\uf019");  // heavy intensity drizzle
        ICON_CODE_MAP.put(310, "\uf017");  // light intensity drizzle rain
        ICON_CODE_MAP.put(311, "\uf019");  // drizzle rain
        ICON_CODE_MAP.put(312, "\uf019");  // heavy intensity drizzle rain
        ICON_CODE_MAP.put(313, "\uf01a");  // shower rain and drizzle
        ICON_CODE_MAP.put(314, "\uf019");  // heavy shower rain and drizzle
        ICON_CODE_MAP.put(321, "\uf01c");  // shower drizzle
        // Rain
        ICON_CODE_MAP.put(500, "\uf01c");  // light rain
        ICON_CODE_MAP.put(501, "\uf019");  // moderate rain
        ICON_CODE_MAP.put(502, "\uf019");  // heavy intensity rain
        ICON_CODE_MAP.put(503, "\uf019");  // very heavy rain
        ICON_CODE_MAP.put(504, "\uf019");  // extreme rain
        ICON_CODE_MAP.put(511, "\uf017");  // freezing rain
        ICON_CODE_MAP.put(520, "\uf01a");  // light intensity shower rain
        ICON_CODE_MAP.put(521, "\uf01a");  // shower rain
        ICON_CODE_MAP.put(522, "\uf01a");  // heavy intensity shower rain
        ICON_CODE_MAP.put(531, "\uf01d");  // ragged shower rain
        // Snow
        ICON_CODE_MAP.put(600, "\uf01b");  // light snow
        ICON_CODE_MAP.put(601, "\uf01b");  // snow
        ICON_CODE_MAP.put(602, "\uf0b5");  // heavy snow
        ICON_CODE_MAP.put(611, "\uf017");  // sleet
        ICON_CODE_MAP.put(612, "\uf017");  // light shower sleet
        ICON_CODE_MAP.put(615, "\uf017");  // light rain and snow
        ICON_CODE_MAP.put(616, "\uf017");  // rain and snow
        ICON_CODE_MAP.put(620, "\uf017");  // light shower snow
        ICON_CODE_MAP.put(621, "\uf01b");  // shower snow
        ICON_CODE_MAP.put(622, "\uf01b");  // heavy shower snow
        // Atmosphere
        ICON_CODE_MAP.put(701, "\uf014");  // mist
        ICON_CODE_MAP.put(711, "\uf062");  // smoke
        ICON_CODE_MAP.put(721, "\uf0b6");  // haze
        ICON_CODE_MAP.put(731, "\uf063");  // sand/ dust whirls
        ICON_CODE_MAP.put(741, "\uf014");  // fog
        ICON_CODE_MAP.put(761, "\uf063");  // dust
        ICON_CODE_MAP.put(762, "\uf063");  // volcanic ash
        ICON_CODE_MAP.put(771, "\uf011");  // squalls
        ICON_CODE_MAP.put(781, "\uf056");  // tornado
        // Clear and clouds
        ICON_CODE_MAP.put(800, "\uf00d");  // clear sky
        ICON_CODE_MAP.put(801, "\uf041");  // few clouds: 11-25%
        ICON_CODE_MAP.put(802, "\uf041");  // scattered clouds: 25-50%
        ICON_CODE_MAP.put(803, "\uf013");  // broken clouds: 51-84%
        ICON_CODE_MAP.put(804, "\uf013");  // overcast clouds: 85-100%
    }

    /**
     * Retrieves the appropriate weather icon code based on the weather condition code and day/night status.
     * 
     * @param conditionCode the weather condition code as provided by the OpenWeatherMap API
     * @param isDayTime boolean flag indicating whether it is day (true) or night (false)
     * @return a string representing the Unicode character for the corresponding weather icon. 
     *         If no specific icon matches, a default icon is returned.
     */
    public static String getIconCode(int conditionCode, boolean isDayTime) {
        String icon = ICON_CODE_MAP.getOrDefault(conditionCode, "\uf07b"); // default icon
        if (!isDayTime) {
            if (icon.equals("\uf00d")) { // clear sky
                return "\uf02e"; // clear sky night
            } else if (icon.equals("\uf01d")) { // clouds
                return "\uf086"; // night clouds
            }
        }
        return icon;
    }
}
