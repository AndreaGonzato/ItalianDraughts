package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class BlackTileTest {

    @Test
    void checkNeighborsX0Y0() {
        BlackTile blackTile = new BlackTile(0, 0);

        blackTile.addNeighbors();
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("bottomRight", new BlackTile(1, 1));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void checkNeighborsX1Y1() {
        BlackTile blackTile = new BlackTile(1, 1);

        blackTile.addNeighbors();
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(0, 0));
        expectedMap.put("topRight", new BlackTile(2, 0));
        expectedMap.put("bottomLeft", new BlackTile(0, 2));
        expectedMap.put("bottomRight", new BlackTile(2, 2));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void checkNeighborsX7Y7() {
        BlackTile blackTile = new BlackTile(7, 7);

        blackTile.addNeighbors();
        Map<String, BlackTile> expectedMap = new HashMap<>();
        expectedMap.put("topLeft", new BlackTile(6, 6));

        Assertions.assertEquals(expectedMap, blackTile.getNeighbors());

    }

    @Test
    void isEmptyAfterRemovingPiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        new BlackPiece(blackTile);
        blackTile.removePiece();

        Assertions.assertTrue(blackTile.isEmpty());
    }

    @Test
    void isFullAfterPlacingPiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        new BlackPiece(blackTile);

        Assertions.assertFalse(blackTile.isEmpty());

    }

    @Test
    void checkBlackTileCasting() {
        Tile tile = new BlackTile(0, 0);
        BlackTile actualBlackTile = BlackTile.asBlackTile(tile);

        BlackTile expectedBlackTile = new BlackTile(0, 0);

        Assertions.assertEquals(expectedBlackTile, actualBlackTile);

    }

    @Test
    void checkRemovedPiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        Piece expectedPiece = new BlackPiece(blackTile);

        Assertions.assertEquals(expectedPiece, blackTile.removePiece());

    }



    public static void addNeighbors(Board board) {

        BlackTile.asBlackTile(board.getTiles()[0][0]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[0][2]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[0][4]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[0][6]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[1][1]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[1][3]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[1][5]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[1][7]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[2][0]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[2][2]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[2][4]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[2][6]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[3][1]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[3][3]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[3][5]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[3][7]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[4][0]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[4][2]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[4][4]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[4][6]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[5][1]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[5][3]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[5][5]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[5][7]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[6][0]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[6][2]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[6][4]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[6][6]).addNeighbors();

        BlackTile.asBlackTile(board.getTiles()[7][1]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[7][3]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[7][5]).addNeighbors();
        BlackTile.asBlackTile(board.getTiles()[7][7]).addNeighbors();
    }


}
