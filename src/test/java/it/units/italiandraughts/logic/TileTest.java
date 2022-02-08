package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.units.italiandraughts.logic.tile.Tile.areValidCoordinatesBiPredicate;

public class TileTest {

    @Test
    void generateBlackTile() {
        int x = 0, y = 0;
        BlackTile expected = new BlackTile(x, y);
        Tile actual = Tile.generateTile(x, y);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void generateTile() {
        int x = 1, y = 0;
        Tile expected = new Tile(x, y);
        Tile actual = Tile.generateTile(x, y);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void areValidCoordinatesBiPredicateX0Y0() {

        Assertions.assertTrue(areValidCoordinatesBiPredicate.test(0, 0));
    }

    @Test
    void areValidCoordinatesBiPredicateX9Y0() {

        Assertions.assertFalse(areValidCoordinatesBiPredicate.test(9, 0));
    }
}
