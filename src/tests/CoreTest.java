package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import engine.GameState;
import screen.TitleScreen;
import static org.junit.jupiter.api.Assertions.*;

class CoreTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGameStateInitializationSinglePlayer() {
        int p2Lives = (TitleScreen.getNumberOfPlayers() == 2) ? 3 : 0;
        GameState gameState = new GameState(1, 0, 3, p2Lives, 0, 0, 100);
        
        assertNotNull(gameState, "GameState should be created successfully");
        assertEquals(3, gameState.getLivesRemaining(), "Player 1 should have 3 lives");
    }

    @Test
    void testGameStateInitializationTwoPlayers() {
        int p2Lives = 3;
        GameState gameState = new GameState(1, 0, 3, p2Lives, 0, 0, 100);
        
        assertNotNull(gameState, "GameState should be created successfully");
        assertEquals(3, gameState.getLivesRemaining(), "Player 1 should have 3 lives");
        assertEquals(3, gameState.getLivesRemainingP2(), "Player 2 should have 3 lives in 2-player mode");
    }

    @Test
    void testGameStateWithZeroP2Lives() {
        GameState gameState = new GameState(1, 0, 3, 0, 0, 0, 100);
        
        assertEquals(3, gameState.getLivesRemaining(), "Player 1 should have 3 lives");
        assertEquals(0, gameState.getLivesRemainingP2(), "Player 2 should have 0 lives in single player mode");
    }

    @Test
    void testGameStateLevelProgression() {
        GameState initialState = new GameState(1, 0, 3, 3, 0, 0, 100);
        
        int p2Lives = (TitleScreen.getNumberOfPlayers() == 2) ? initialState.getLivesRemainingP2() : 0;
        
        GameState nextLevel = new GameState(
            initialState.getLevel() + 1,
            initialState.getScore() + 100,
            initialState.getLivesRemaining(),
            p2Lives,
            initialState.getBulletsShot() + 10,
            initialState.getShipsDestroyed() + 5,
            initialState.getCoin() + 50
        );
        
        assertEquals(2, nextLevel.getLevel(), "Level should increment to 2");
        assertEquals(100, nextLevel.getScore(), "Score should be updated");
    }

    @Test
    void testGameContinuationConditionSinglePlayer() {
        GameState gameState = new GameState(1, 0, 3, 0, 0, 0, 100);
        
        assertTrue(gameState.getLivesRemaining() > 0, "Game should continue when P1 has lives in single player mode");
    }

    @Test
    void testGameContinuationConditionTwoPlayers() {
        GameState state1 = new GameState(1, 0, 3, 3, 0, 0, 100);
        assertTrue(state1.getLivesRemaining() > 0 || state1.getLivesRemainingP2() > 0,
            "Game should continue when either player has lives");
        
        GameState state2 = new GameState(1, 0, 3, 0, 0, 0, 100);
        assertTrue(state2.getLivesRemaining() > 0, "Game should continue when P1 has lives");
        
        GameState state3 = new GameState(1, 0, 0, 3, 0, 0, 100);
        assertTrue(state3.getLivesRemainingP2() > 0, "Game should continue when P2 has lives");
        
        GameState state4 = new GameState(1, 0, 0, 0, 0, 0, 100);
        assertFalse(state4.getLivesRemaining() > 0 || state4.getLivesRemainingP2() > 0,
            "Game should end when both players have no lives");
    }

    @Test
    void testNumberOfPlayersAffectsGameState() {
        int numberOfPlayers = TitleScreen.getNumberOfPlayers();
        int expectedP2Lives = (numberOfPlayers == 2) ? 3 : 0;
        
        GameState gameState = new GameState(1, 0, 3, expectedP2Lives, 0, 0, 100);
        
        if (numberOfPlayers == 1) {
            assertEquals(0, gameState.getLivesRemainingP2(), "P2 should have 0 lives in single player mode");
        } else if (numberOfPlayers == 2) {
            assertTrue(gameState.getLivesRemainingP2() > 0, "P2 should have lives in two player mode");
        }
    }
}
