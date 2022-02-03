package it.units.italiandraughts.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.units.italiandraughts.logic.tile.Tile.areValidCoordinatesBiPredicate;

public class TileTest {
    @Test
    void areValidCoordinatesBiPredicateX0Y0() {

        Assertions.assertTrue(areValidCoordinatesBiPredicate.test(0, 0));
    }

    @Test
    void areValidCoordinatesBiPredicateX9Y0() {

        Assertions.assertFalse(areValidCoordinatesBiPredicate.test(9, 0));
    }
}
