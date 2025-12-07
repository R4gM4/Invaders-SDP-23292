import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import engine.DrawManager;
import engine.Frame;
import screen.TitleScreen;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DrawManager menu drawing functionality.
 * Tests the modifications related to displaying 1 or 2 player option in the menu.
 */
class DrawManagerTest {

    private DrawManager drawManager;
    private Frame frame;

    @BeforeEach
    void setUp() {
        // Get singleton instance of DrawManager
        drawManager = DrawManager.getInstance();
        
        // Initialize frame
        frame = new Frame(448, 520);
        drawManager.setFrame(frame);
    }

    @Test
    void testDrawManagerSingletonInstance() {
        // Test that DrawManager returns the same instance (singleton pattern)
        DrawManager instance1 = DrawManager.getInstance();
        DrawManager instance2 = DrawManager.getInstance();
        assertSame(instance1, instance2, 
            "DrawManager should return the same singleton instance");
    }

    @Test
    void testDrawManagerNotNull() {
        // Test that DrawManager instance is not null
        assertNotNull(drawManager, "DrawManager instance should not be null");
    }

    @Test
    void testFrameCanBeSet() {
        // Test that frame can be set on DrawManager
        Frame testFrame = new Frame(448, 520);
        assertDoesNotThrow(() -> drawManager.setFrame(testFrame),
            "Setting frame should not throw an exception");
    }

    @Test
    void testDrawMenuWithPlayerSelectionOption() {
        // Test that drawMenu can be called with option 7 (player selection)
        TitleScreen screen = new TitleScreen(448, 520, 60);
        
        // Initialize drawing context
        drawManager.initDrawing(screen);
        
        // Test that drawMenu doesn't throw exception with option 7
        assertDoesNotThrow(() -> drawManager.drawMenu(screen, 7),
            "drawMenu should handle option 7 (player selection) without error");
    }

    @Test
    void testDrawMenuWithAllOptions() {
        // Test that drawMenu works with all menu options including the new one
        TitleScreen screen = new TitleScreen(448, 520, 60);
        drawManager.initDrawing(screen);
        
        int[] options = {0, 2, 3, 4, 5, 6, 7}; // All valid menu options
        
        for (int option : options) {
            assertDoesNotThrow(() -> drawManager.drawMenu(screen, option),
                "drawMenu should handle option " + option + " without error");
        }
    }

    @Test
    void testPlayerSelectionOptionIntegration() {
        // Test integration with TitleScreen's numberOfPlayers
        TitleScreen screen = new TitleScreen(448, 520, 60);
        drawManager.initDrawing(screen);
        
        // This should work regardless of numberOfPlayers value
        assertDoesNotThrow(() -> {
            drawManager.drawMenu(screen, 7);
            drawManager.completeDrawing(screen);
        }, "Drawing menu with player selection should complete without error");
    }
}
