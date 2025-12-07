package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private GameState state;

    @BeforeEach
    void setup() {
        state = new GameState(0, 0, 3, 3, 0, 0, 10); // level 0, 10 coins
    }

    @Test
    void testInitialCoins() {
        assertEquals(10, state.getCoin(), "Initial coin should be 10");
    }

    @Test
    void testAddCoins() {
        state.addCoins(5);
        assertEquals(15, state.getCoin(), "Coin should increase to 15");
    }

    @Test
    void testDeductCoins() {
        assertTrue(state.deductCoins(5), "Deducting 5 should succeed");
        assertEquals(5, state.getCoin(), "Coin should be 5 after deduction");
    }

    @Test
    void testDeductCoinsFail() {
        assertFalse(state.deductCoins(20), "Cannot deduct more coins than owned");
        assertEquals(10, state.getCoin(), "Coin should stay 10 after failed deduction");
    }

    @Test
    void testSetLevel() {
        state.setLevel(3);
        assertEquals(3, state.getLevel(), "Level should be set to 3");
    }

    @Test
    void testUnlockLevel() {
        assertFalse(state.isLevelUnlocked(1), "Level 1 should be locked initially");
        state.unlockLevel(1);
        assertTrue(state.isLevelUnlocked(1), "Level 1 should now be unlocked");
    }

    @Test
    void testUnlockNextLevel() {
        state.setLevel(2);
        state.unlockNextLevel();
        assertTrue(state.isLevelUnlocked(2), "Next level should be unlocked");
    }
}
