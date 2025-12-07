package screen;

import engine.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelsScreenTest {

    private LevelsScreen screen;
    private GameState gameState;

    @BeforeEach
    void setup() {
        gameState = new GameState(); // Assurez-vous que GameState a un constructeur vide ou ajustez
        screen = new LevelsScreen(gameState, 800, 600, 60);
    }

    @Test
    void testConstructor() {
        assertEquals(0, screen.selectedLevelIndex, "selectedLevelIndex doit commencer à 0");
        assertEquals(1, screen.returnCode, "returnCode doit être initialisé à 1");
        assertNotNull(screen.levels, "Le tableau levels ne doit pas être null");
        assertEquals(7, screen.levels.length, "Il doit y avoir 7 niveaux");
    }

    @Test
    void testRun() {
        int result = screen.run();
        assertEquals(2, result, "La méthode run() doit retourner 2");
    }

    @Test
    void testStartLevel() {
        screen.startLevel(0);
        assertEquals(1, gameState.getLevel(), "startLevel(0) doit lancer le niveau 1");
        assertFalse(screen.isRunning, "startLevel() doit arrêter l’écran");
    }

    @Test
    void testLevelsContent() {
        String[] expected = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"};
        assertArrayEquals(expected, screen.levels, "Les noms des niveaux doivent correspondre");
    }
}