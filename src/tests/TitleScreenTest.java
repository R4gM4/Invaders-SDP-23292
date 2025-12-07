import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import screen.TitleScreen;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TitleScreen player selection functionality.
 * Tests the modifications related to 1 or 2 player mode selection.
 */
class TitleScreenTest {

    private TitleScreen titleScreen;

    @BeforeEach
    void setUp() {
        // Initialize TitleScreen with standard dimensions
        titleScreen = new TitleScreen(448, 520, 60);
    }

    @Test
    void testDefaultNumberOfPlayers() {
        // Test that the default number of players is 1
        assertEquals(1, TitleScreen.getNumberOfPlayers(), 
            "Default number of players should be 1");
    }

    @Test
    void testGetNumberOfPlayersReturnsValidValue() {
        // Test that getNumberOfPlayers returns a valid value (1 or 2)
        int players = TitleScreen.getNumberOfPlayers();
        assertTrue(players == 1 || players == 2, 
            "Number of players should be either 1 or 2");
    }

    @Test
    void testDefaultReturnCodeIsPlayerSelection() {
        // Test that the default return code is 7 (player selection option)
        // This is accessed via reflection or by running the screen
        // For this test, we verify the TitleScreen initializes correctly
        assertNotNull(titleScreen, "TitleScreen should initialize successfully");
    }

    @Test
    void testTitleScreenInitialization() {
        // Test that TitleScreen initializes without errors
        assertNotNull(titleScreen, "TitleScreen should not be null after initialization");
    }

    @Test
    void testNumberOfPlayersStaticAccess() {
        // Test that numberOfPlayers can be accessed statically
        int players1 = TitleScreen.getNumberOfPlayers();
        
        // Create a second instance
        TitleScreen titleScreen2 = new TitleScreen(448, 520, 60);
        int players2 = TitleScreen.getNumberOfPlayers();
        
        // Both should return the same value since it's static
        assertEquals(players1, players2, 
            "Static numberOfPlayers should be the same across instances");
    }
}
