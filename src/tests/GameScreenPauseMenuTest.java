public class GameScreenPauseMenuTest {

    @org.junit.jupiter.api.BeforeEach
    void resetInput() throws Exception {
        engine.Core.getInputManager();
        java.lang.reflect.Field keysField = engine.InputManager.class.getDeclaredField("keys");
        keysField.setAccessible(true);
        boolean[] keys = (boolean[]) keysField.get(null);
        if (keys == null) {
            return;
        }
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
        }
    }

    @org.junit.jupiter.api.Test
    void handlePauseTogglePausesGameAndTimerWhenEscapeIsPressed() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        engine.GameTimer timer = (engine.GameTimer) getField(gameScreen, "gameTimer");
        timer.start();
        setKeyState(java.awt.event.KeyEvent.VK_ESCAPE, true);

        invokePrivate(gameScreen, "handlePauseToggle");

        org.junit.jupiter.api.Assertions.assertTrue(getBooleanField(gameScreen, "paused"));
        org.junit.jupiter.api.Assertions.assertTrue(isTimerPaused(timer));
    }

    @org.junit.jupiter.api.Test
    void handlePauseToggleResumesGameAndTimerWhenEscapeIsPressedAgain() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        engine.GameTimer timer = (engine.GameTimer) getField(gameScreen, "gameTimer");
        timer.start();
        timer.pause();
        setBooleanField(gameScreen, "paused", true);
        resetCooldown((engine.Cooldown) getField(gameScreen, "pauseInputCooldown"));
        setKeyState(java.awt.event.KeyEvent.VK_ESCAPE, true);

        invokePrivate(gameScreen, "handlePauseToggle");

        org.junit.jupiter.api.Assertions.assertFalse(getBooleanField(gameScreen, "paused"));
        org.junit.jupiter.api.Assertions.assertFalse(isTimerPaused(timer));
    }

    @org.junit.jupiter.api.Test
    void handlePauseMenuInputMovesSelectionWithArrowKeys() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        setIntField(gameScreen, "pauseSelection", 0);
        resetCooldown((engine.Cooldown) getField(gameScreen, "pauseInputCooldown"));
        setKeyState(java.awt.event.KeyEvent.VK_DOWN, true);

        invokePrivate(gameScreen, "handlePauseMenuInput");

        org.junit.jupiter.api.Assertions.assertEquals(1, getIntField(gameScreen, "pauseSelection"));

        setKeyState(java.awt.event.KeyEvent.VK_DOWN, false);
        resetCooldown((engine.Cooldown) getField(gameScreen, "pauseInputCooldown"));
        setKeyState(java.awt.event.KeyEvent.VK_UP, true);

        invokePrivate(gameScreen, "handlePauseMenuInput");

        org.junit.jupiter.api.Assertions.assertEquals(0, getIntField(gameScreen, "pauseSelection"));
    }

    @org.junit.jupiter.api.Test
    void pauseMenuContinueResumesGame() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        engine.GameTimer timer = (engine.GameTimer) getField(gameScreen, "gameTimer");
        timer.start();
        timer.pause();
        setBooleanField(gameScreen, "paused", true);
        setKeyState(java.awt.event.KeyEvent.VK_SPACE, true);

        invokePrivate(gameScreen, "handlePauseMenuInput");

        org.junit.jupiter.api.Assertions.assertFalse(getBooleanField(gameScreen, "paused"));
        org.junit.jupiter.api.Assertions.assertFalse(isTimerPaused(timer));
        org.junit.jupiter.api.Assertions.assertEquals(screen.GameScreen.ExitAction.NONE,
                getField(gameScreen, "exitAction"));
    }

    @org.junit.jupiter.api.Test
    void pauseMenuRestartSetsExitActionAndStopsScreen() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        setIntField(gameScreen, "pauseSelection", 1);
        setKeyState(java.awt.event.KeyEvent.VK_ENTER, true);

        invokePrivate(gameScreen, "handlePauseMenuInput");

        org.junit.jupiter.api.Assertions.assertEquals(screen.GameScreen.ExitAction.RESTART,
                getField(gameScreen, "exitAction"));
        org.junit.jupiter.api.Assertions.assertFalse(getBooleanFieldFromScreen(gameScreen, "isRunning"));
    }

    @org.junit.jupiter.api.Test
    void pauseMenuReturnToMenuSetsExitActionAndStopsScreen() throws Exception {
        screen.GameScreen gameScreen = createPauseReadyGameScreen();
        setIntField(gameScreen, "pauseSelection", 2);
        setKeyState(java.awt.event.KeyEvent.VK_ENTER, true);

        invokePrivate(gameScreen, "handlePauseMenuInput");

        org.junit.jupiter.api.Assertions.assertEquals(screen.GameScreen.ExitAction.MENU,
                getField(gameScreen, "exitAction"));
        org.junit.jupiter.api.Assertions.assertFalse(getBooleanFieldFromScreen(gameScreen, "isRunning"));
    }

    private screen.GameScreen createPauseReadyGameScreen() throws Exception {
        engine.GameState gameState = new engine.GameState(1, 0, 3, 3, 0, 0, 0);
        engine.level.Level level = new engine.level.Level(1, 1, 1, 1, 1);
        screen.GameScreen gameScreen = new screen.GameScreen(gameState, level, false, 3, 448, 520, 60);
        engine.Core.getInputManager();

        engine.Cooldown pauseCooldown = engine.Core.getCooldown(0);
        resetCooldown(pauseCooldown);
        setField(gameScreen, "pauseInputCooldown", pauseCooldown);

        engine.Cooldown inputDelay = engine.Core.getCooldown(0);
        resetCooldown(inputDelay);
        setField(gameScreen, "inputDelay", inputDelay);

        engine.GameTimer timer = new engine.GameTimer();
        setField(gameScreen, "gameTimer", timer);
        setBooleanField(gameScreen, "paused", false);
        setIntField(gameScreen, "pauseSelection", 0);
        setBooleanField(gameScreen, "isRunning", true);

        return gameScreen;
    }

    private void invokePrivate(Object target, String methodName) throws Exception {
        java.lang.reflect.Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(target);
    }

    private void setKeyState(int keyCode, boolean pressed) throws Exception {
        java.lang.reflect.Field keysField = engine.InputManager.class.getDeclaredField("keys");
        keysField.setAccessible(true);
        boolean[] keys = (boolean[]) keysField.get(null);
        if (keys == null) {
            engine.Core.getInputManager();
            keys = (boolean[]) keysField.get(null);
        }
        keys[keyCode] = pressed;
    }

    private void resetCooldown(engine.Cooldown cooldown) throws Exception {
        java.lang.reflect.Field timeField = engine.Cooldown.class.getDeclaredField("time");
        timeField.setAccessible(true);
        timeField.setLong(cooldown, 0L);
    }

    private boolean isTimerPaused(engine.GameTimer timer) throws Exception {
        return getBooleanField(timer, "paused");
    }

    private Object getField(Object target, String fieldName) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    private int getIntField(Object target, String fieldName) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        return field.getInt(target);
    }

    private boolean getBooleanField(Object target, String fieldName) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        return field.getBoolean(target);
    }

    private boolean getBooleanFieldFromScreen(Object target, String fieldName) throws Exception {
        return getBooleanField(target, fieldName);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void setBooleanField(Object target, String fieldName, boolean value) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.setBoolean(target, value);
    }

    private void setIntField(Object target, String fieldName, int value) throws Exception {
        java.lang.reflect.Field field = findField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.setInt(target, value);
    }

    private java.lang.reflect.Field findField(Class<?> type, String name) throws Exception {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }
}
