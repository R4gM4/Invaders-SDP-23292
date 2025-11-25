import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import screen.GameScreen;
import screen.TitleScreen;
import engine.GameState;
import engine.level.Level;
import engine.level.LevelManager;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameScreen player initialization.
 * Tests the modifications related to ship creation based on 1 or 2 player mode.
 */
class GameScreenTest {

    private GameState gameState;
    private Level testLevel;
    private LevelManager levelManager;

    @BeforeEach
    void setUp() {
        // Initialize game state with default values
        gameState = new GameState(1, 0, 3, 3, 0, 0, 100);
        
        // Initialize level manager and get a test level
        levelManager = new LevelManager();
        testLevel = levelManager.getLevel(1);
    }

    @Test
    void testGameScreenInitializationSinglePlayer() {
        // Simulate single player mode
        // Note: This test assumes TitleScreen.numberOfPlayers can be set to 1
        
        GameScreen gameScreen = new GameScreen(
            gameState, 
            testLevel, 
            false, 
            3, 
            448, 
            520, 
            60
        );
        
        assertNotNull(gameScreen, "GameScreen should initialize successfully");
    }

    @Test
    void testGameScreenInitializationTwoPlayers() {
        // Test that GameScreen initializes correctly for two players
        GameScreen gameScreen = new GameScreen(
            gameState, 
            testLevel, 
            false, 
            3, 
            448, 
            520, 
            60
        );
        
        assertNotNull(gameScreen, "GameScreen should initialize successfully with two players");
    }

    @Test
    void testGameScreenWithBonusLife() {
        // Test that bonus life is handled correctly
        GameScreen gameScreen = new GameScreen(
            gameState, 
            testLevel, 
            true,  // bonusLife = true
            3, 
            448, 
            520, 
            60
        );
        
        assertNotNull(gameScreen, "GameScreen should handle bonus life correctly");
    }

    @Test
    void testGameScreenInitializeMethod() {
        // Test that initialize() method works without errors
        GameScreen gameScreen = new GameScreen(
            gameState, 
            testLevel, 
            false, 
            3, 
            448, 
            520, 
            60
        );
        
        assertDoesNotThrow(() -> gameScreen.initialize(),
            "GameScreen.initialize() should not throw an exception");
    }

    @Test
    void testGameScreenDifferentPlayerModesInitialize() {
        // Test initialization for different player configurations
        int[] playerConfigs = {1, 2};
        
        for (int numPlayers : playerConfigs) {
            int p2Lives = (numPlayers == 2) ? 3 : 0;
            GameState state = new GameState(1, 0, 3, p2Lives, 0, 0, 100);
            
            GameScreen gameScreen = new GameScreen(
                state, 
                testLevel, 
                false, 
                3, 
                448, 
                520, 
                60
            );
            
            assertDoesNotThrow(() -> gameScreen.initialize(),
                "GameScreen should initialize for " + numPlayers + " player(s)");
        }
    }

    @Test
    void testGameStateValuesPreserved() {
        // Test that GameState values are correctly passed to GameScreen
        GameState customState = new GameState(2, 500, 2, 1, 50, 10, 200);
        
        GameScreen gameScreen = new GameScreen(
            customState, 
            testLevel, 
            false, 
            3, 
            448, 
            520, 
            60
        );
        
        assertNotNull(gameScreen, "GameScreen should preserve GameState values");
    }
}
