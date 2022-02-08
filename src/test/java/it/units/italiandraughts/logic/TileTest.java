package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.units.italiandraughts.logic.tile.Tile.areValidCoordinatesBiPredicate;
import static it.units.italiandraughts.logic.tile.Tile.generateTile;

public class TileTest {

    @Test
    void areValidCoordinatesBiPredicateX0Y0() {
        Assertions.assertTrue(areValidCoordinatesBiPredicate.test(0, 0));
    }

    @Test
    void areValidCoordinatesBiPredicateX9Y0() {
        Assertions.assertFalse(areValidCoordinatesBiPredicate.test(9, 0));
    }

    @Test
    void checkBlackTileGeneration() {
        int x = 0, y = 0;
        BlackTile expected = new BlackTile(x, y);
        Tile actual = Tile.generateTile(x, y);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkTileGeneration() {
        int x = 1, y = 0;
        Tile expected = new Tile(x, y);
        Tile actual = Tile.generateTile(x, y);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkToString() {
        String actual = "Tile{x=0, y=0}";
        Tile tile = generateTile(0, 0);

        Assertions.assertEquals(tile.toString(), actual);
    }

}
