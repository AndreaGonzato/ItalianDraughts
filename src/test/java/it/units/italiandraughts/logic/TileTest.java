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
    void getDirectionTop1(){
        BlackTile blackTile1 = new BlackTile(3, 3);
        BlackTile blackTile2 = new BlackTile(2, 2);

        String actualString = blackTile1.getDirection(blackTile2);
        String expectedString = "top";

        Assertions.assertEquals(actualString, expectedString);
    }


    @Test
    void getDirectionTop2(){
        BlackTile blackTile1 = new BlackTile(4, 4);
        BlackTile blackTile2 = new BlackTile(5, 3);

        String actualString = blackTile1.getDirection(blackTile2);
        String expectedString = "top";

        Assertions.assertEquals(actualString, expectedString);
    }

    @Test
    void getDirectionBottom1(){
        BlackTile blackTile1 = new BlackTile(6, 6);
        BlackTile blackTile2 = new BlackTile(5, 7);

        String actualString = blackTile1.getDirection(blackTile2);
        String expectedString = "bottom";

        Assertions.assertEquals(actualString, expectedString);
    }


    @Test
    void getDirectionBottom2(){
        BlackTile blackTile1 = new BlackTile(0, 0);
        BlackTile blackTile2 = new BlackTile(1, 1);

        String actualString = blackTile1.getDirection(blackTile2);
        String expectedString = "bottom";

        Assertions.assertEquals(actualString, expectedString);
    }

    @Test
    void checkToString() {
        String actual = "Tile{x=0, y=0}";
        Tile tile = generateTile(0, 0);

        Assertions.assertEquals(tile.toString(), actual);
    }



}
