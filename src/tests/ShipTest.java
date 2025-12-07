package tests;

import entity.Ship;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipTest implements Test {


    public void testSetColor() {
        Ship ship = new Ship(100, 100, Color.RED);

        ship.setColor(Color.RED);
        assertEquals(Color.RED, ship.getColor());

        ship.setColor(Color.BLUE);
        assertEquals(Color.BLUE, ship.getColor());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
