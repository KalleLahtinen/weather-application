package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for methods in ApplicationStateManager class.
 * 
 * @author Roope Kärkkäinen
 */
public class ApplicationStateManagerTest {

    private ApplicationStateManager stateManager;

    @BeforeEach
    public void setUp() {
        stateManager = new ApplicationStateManager();
    }

    @Test
    public void testDefaultValues() {
        assertEquals("metric", stateManager.getUnits());
        assertEquals("Helsinki", stateManager.getCurrentCity());
        assertTrue(stateManager.getFavourites().isEmpty());
    }

    @Test
    public void testAddFavoriteCity() {
        stateManager.addFavoriteCity("London");
        assertEquals(1, stateManager.getFavourites().size());
        assertTrue(stateManager.getFavourites().contains("London"));
    }

    @Test
    public void testAddDuplicateFavoriteCity() {
        stateManager.addFavoriteCity("Paris");
        stateManager.addFavoriteCity("Paris");
        assertEquals(1, stateManager.getFavourites().size());
    }

    @Test
    public void testRemoveFavoriteCity() {
        stateManager.addFavoriteCity("Tokyo");
        assertTrue(stateManager.removeFavoriteCity("Tokyo"));
        assertFalse(stateManager.getFavourites().contains("Tokyo"));
    }

    @Test
    public void testRemoveNonExistingFavoriteCity() {
        assertFalse(stateManager.removeFavoriteCity("Sydney"));
    }

    @Test
    public void testRemoveNonExistingCityFromHistory() {
        assertFalse(stateManager.removeCityFromHistory("Oslo"));
    }
}
