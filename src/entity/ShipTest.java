package entity;

import junit.framework.TestCase;
import java.awt.Color;
import engine.Cooldown;

public class ShipTest extends TestCase {


    public void testSetColor() {
        Ship ship = new Ship(100, 100, Color.RED);

        ship.setColor(Color.RED);
        assertEquals(Color.RED, ship.getColor());

        ship.setColor(Color.BLUE);
        assertEquals(Color.BLUE, ship.getColor());
    }
}
