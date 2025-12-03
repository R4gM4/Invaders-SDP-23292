package engine;

import engine.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screen.GameScreen;
import screen.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DrawManagerTest {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 120;

    private Graphics2D attachBackBuffer(final BufferedImage canvas) throws Exception {
        Graphics2D graphics = canvas.createGraphics();
        Field backBufferField = DrawManager.class.getDeclaredField("backBufferGraphics");
        backBufferField.setAccessible(true);
        backBufferField.set(null, graphics);
        return graphics;
    }

    private void resetGameScreen(final DrawManager drawManager) throws Exception {
        Field gameScreenField = DrawManager.class.getDeclaredField("gameScreen");
        gameScreenField.setAccessible(true);
        gameScreenField.set(drawManager, null);
    }

    @BeforeEach
    void resetState() throws Exception {
        DrawManager manager = DrawManager.getInstance();
        resetGameScreen(manager);

        Field backBufferField = DrawManager.class.getDeclaredField("backBufferGraphics");
        backBufferField.setAccessible(true);
        backBufferField.set(null, null);
    }

    @Test
    void testDrawBackgroundGradient() throws Exception {
        DrawManager drawManager = DrawManager.getInstance();
        BufferedImage canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = attachBackBuffer(canvas);

        GameState state = new GameState(2, 0, 3, 3, 0, 0, 0);
        Level dummyLevel = new Level(2, 5, 5, 1, 1);
        GameScreen screen = new GameScreen(state, dummyLevel, false, 3, WIDTH, HEIGHT, 60);

        try {
            drawManager.drawBackground(screen);
        } finally {
            graphics.dispose();
        }

        int topPixel = canvas.getRGB(WIDTH / 2, 0);
        int bottomPixel = canvas.getRGB(WIDTH / 2, HEIGHT - 1);
        assertNotEquals(topPixel, bottomPixel, "The background should be blurred");
    }

    @Test
    void testDrawBackgroundBlackWithoutGameScreen() throws Exception {
        DrawManager drawManager = DrawManager.getInstance();
        BufferedImage canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = attachBackBuffer(canvas);

        resetGameScreen(drawManager);
        Screen genericScreen = new Screen(WIDTH, HEIGHT, 60) { };

        try {
            drawManager.drawBackground(genericScreen);
        } finally {
            graphics.dispose();
        }

        int pixel = canvas.getRGB(WIDTH / 2, HEIGHT / 2);
        assertEquals(0xFF000000, pixel, "The background must be black if GameScreen is not activated");
    }
}
