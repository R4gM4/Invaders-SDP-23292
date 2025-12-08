package screen;

import engine.Core;
import engine.Cooldown;
import engine.GameState;

import java.awt.event.KeyEvent;

/**
 * Levels selection screen
 */
public class LevelsScreen extends Screen {

    private static final int SELECTION_TIME = 200;

    private final Cooldown selectionCooldown;
    private final GameState gameState;

    private int selectedLevelIndex;

    // available levels
    private final String[] levels = {
            "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"
    };

    public LevelsScreen(GameState gameState, int width, int height, int fps) {
        super(width, height, fps);
        this.gameState = gameState;
        this.selectedLevelIndex = 0;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.returnCode = 1;
    }

    @Override
    public int run() {
        super.run();
        return this.returnCode;
    }

    private void startLevel(int index) {
        gameState.setLevel(index + 1);
        this.returnCode = 2;
        this.isRunning = false;
    }

    @Override
    protected void update() {
        super.update();
        draw();

        if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
            this.returnCode = 1;
            this.isRunning = false;
            return;
        }

        if (selectionCooldown.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP) || inputManager.isKeyDown(KeyEvent.VK_K)) {
                int newIndex = selectedLevelIndex;
                for (int i = 0; i < levels.length; i++) {
                    newIndex = (newIndex - 1 + levels.length) % levels.length;
                    if (gameState.isLevelUnlocked(newIndex)) {
                        selectedLevelIndex = newIndex;
                        break;
                    }
                }
                selectionCooldown.reset();
            }

            if (inputManager.isKeyDown(KeyEvent.VK_DOWN) || inputManager.isKeyDown(KeyEvent.VK_S)) {
                int newIndex = selectedLevelIndex;
                for (int i = 0; i < levels.length; i++) {
                    newIndex = (newIndex + 1) % levels.length;
                    if (gameState.isLevelUnlocked(newIndex)) {
                        selectedLevelIndex = newIndex;
                        break;
                    }
                }
                selectionCooldown.reset();
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && gameState.isLevelUnlocked(selectedLevelIndex)) {
                startLevel(selectedLevelIndex);
                selectionCooldown.reset();
            }
        }
    }

    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawLevelsScreenHeader(this);

        int startY = 150;
        int lineHeight = 35;

        for (int i = 0; i < levels.length; i++) {
            boolean locked = !gameState.isLevelUnlocked(i);
            boolean selected = i == selectedLevelIndex;
            drawManager.drawLevelItem(this, levels[i], startY + i * lineHeight, selected, locked);
        }

        drawManager.completeDrawing(this);
    }

}
