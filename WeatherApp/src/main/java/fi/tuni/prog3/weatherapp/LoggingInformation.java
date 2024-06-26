package fi.tuni.prog3.weatherapp;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    ChatGPT 3.5 was mainly used in the creation of this class as help with 
    troubleshooting and generating Javadoc comments.
*/

/**
 * A class for logging information, including errors, and saving errors to a file.
 * 
 * @author Roope Kärkkäinen
 */
public class LoggingInformation {
    private static final Logger logger = LogManager.getLogger(LoggingInformation.class);
    private static final String ERROR_LOG_FILE = "error.log";

    /**
     * Default constructor for LoggingInformation class
     */
    public LoggingInformation() {}
    
    /**
     * Logs an error message with the specified message and exception.
     * Saves the error to a file.
     * 
     * @param message The error message.
     * @param exception The exception causing the error.
     */
    public static void logError(String message, Throwable exception) {
        saveErrorToFile(message, exception);
    }

    /**
     * Saves the error message and exception to a file.
     * 
     * @param message The error message.
     * @param exception The exception causing the error.
     */
    private static void saveErrorToFile(String message, Throwable exception) {
        // Create a directory named "errors" if it doesn't exist
        File directory = new File("errors");
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        // Format current time in a file-friendly manner
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        
        String fileName = "errors/error_" + formattedDateTime  + ".log";
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
