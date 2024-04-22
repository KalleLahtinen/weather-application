package fi.tuni.prog3.weatherapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * A class for logging information, including errors, and saving errors to a file.
 * @author Roope Kärkkäinen
 */
public class LoggingInformation {
    private static final Logger logger = LogManager.getLogger(LoggingInformation.class);
    private static final String ERROR_LOG_FILE = "error.log";

    /**
     * Logs an error message with the specified message and exception.
     * Saves the error to a file.
     * @param message The error message.
     * @param exception The exception causing the error.
     */
    public static void logError(String message, Throwable exception) {
        logger.error(message, exception);
        saveErrorToFile(message, exception);
    }

    /**
     * Saves the error message and exception to a file.
     * @param message The error message.
     * @param exception The exception causing the error.
     */
    private static void saveErrorToFile(String message, Throwable exception) {
        String fileName = "error_" + LocalDateTime.now() + ".log";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println("Timestamp: " + LocalDateTime.now());
            writer.println("Message: " + message);
            writer.println("Exception:");
            exception.printStackTrace(writer);
            writer.println();
        } catch (IOException e) {
            // If unable to save error to file, log it
            logger.error("Error saving error to file", e);
        }
    }
}
