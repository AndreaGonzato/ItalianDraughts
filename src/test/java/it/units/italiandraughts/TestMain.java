package it.units.italiandraughts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMain {
    @Test
    void testAddOne() {
        int number = 23;
        Assertions.assertEquals(24, ItalianDraughts.addOne(number));
    }
}
