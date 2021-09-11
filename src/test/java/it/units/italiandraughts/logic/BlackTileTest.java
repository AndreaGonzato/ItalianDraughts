package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static it.units.italiandraughts.logic.BoardTest.initTiles;

public class BlackTileTest {

    @Test
    void getPiece() {
        BlackTile blackTile = new BlackTile(0, 0);

        Piece expectedPiece = new Piece(PieceColor.WHITE, blackTile);
        Piece actualPiece = blackTile.getPiece();

        Assertions.assertEquals(expectedPiece, actualPiece);

    }

    @Test
    void getNeighborsX0Y0() {
        BlackTile blackTile = new BlackTile(0, 0);
        Tile[][] tiles = initTiles();

        blackTile.addNeighbors(tiles);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("bottomRight", new BlackTile(1, 1));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void getNeighborsX1Y1() {
        BlackTile blackTile = new BlackTile(1, 1);
        Tile[][] tiles = initTiles();

        blackTile.addNeighbors(tiles);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(0, 0));
        expectedMap.put("topRight", new BlackTile(2, 0));
        expectedMap.put("bottomLeft", new BlackTile(0, 2));
        expectedMap.put("bottomRight", new BlackTile(2, 2));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void getNeighborsX7Y7() {
        BlackTile blackTile = new BlackTile(7, 7);
        Tile[][] tiles = initTiles();

        blackTile.addNeighbors(tiles);
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(6, 6));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void isEmptyOnEmptyBlackTile() {
        BlackTile blackTile = new BlackTile(0, 0);

        Assertions.assertEquals(true, blackTile.isEmpty());

    }

    @Test
    void isEmptyOnFullBlackTile() {
        BlackTile blackTile = new BlackTile(0, 0);
        blackTile.placePiece(new Piece(PieceColor.BLACK));

        Assertions.assertEquals(false, blackTile.isEmpty());

    }

    @Test
    void asBlackTile() {
        Tile tile = new BlackTile(0, 0);
        BlackTile actualBlackTile = BlackTile.asBlackTile(tile);

        BlackTile expectedBlackTile = new BlackTile(0, 0);

        Assertions.assertEquals(expectedBlackTile, actualBlackTile);

    }


}
