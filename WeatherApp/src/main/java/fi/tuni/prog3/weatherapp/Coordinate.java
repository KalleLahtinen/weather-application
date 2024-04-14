package fi.tuni.prog3.weatherapp;

/**
 * Represents a geographical coordinate with a latitude and a longitude.
 */
public class Coordinate {
    private final double latitude;
    private final double longitude;

    /**
     * Constructs a Coordinate object with specified latitude and longitude.
     * @param latitude the latitude of the coordinate, expressed as a double
     * @param longitude the longitude of the coordinate, expressed as a double
     */
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the latitude of this coordinate.
     * @return the latitude as a double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of this coordinate.
     * @return the longitude as a double
     */
    public double getLongitude() {
        return longitude;
    }
}