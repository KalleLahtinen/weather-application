package fi.tuni.prog3.weatherapp;

/**
 * Interface with methods to read from a file and write to a file.
 */
public interface iReadAndWriteToFile {

    /**
     * Reads JSON from the given file.
     * 
     * @return A ApplicationStateManager object created based on read file, 
     *         a fresh instance if file could not be read.
     */
    public ApplicationStateManager readFromFile();

    /**
     * Write the student progress as JSON into the given file.
     * 
     * @param appState The ApplicationStateManager object to be written to {@code fileName}.
     */
    public void writeToFile(ApplicationStateManager appState);
}
