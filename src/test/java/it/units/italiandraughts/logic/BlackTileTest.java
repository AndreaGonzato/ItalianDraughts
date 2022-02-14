package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlackTileTest {
    // TEST associated class : completed


    @Test
    void checkNeighborsX0Y0() {
        Board board = Board.reset();

        BlackTile blackTile = BlackTile.asBlackTile(board.getTiles()[0][0]);

        BlackTile expectedBlackTile = new BlackTile(1, 1);

        BlackTile actualBlackTile = blackTile.getNeighbors().get("bottomRight");
        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
    }

    @Test
    void checkNeighborsX1Y1() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.reset().getTiles()[1][1]);

        BlackTile expectedBlackTile1 = new BlackTile(0, 0);
        BlackTile expectedBlackTile2 = new BlackTile(2, 0);
        BlackTile expectedBlackTile3 = new BlackTile(0, 2);
        BlackTile expectedBlackTile4 = new BlackTile(2, 2);

        Assertions.assertTrue(expectedBlackTile1.equals(blackTile.getNeighbors().get("topLeft"))
                    && expectedBlackTile2.equals(blackTile.getNeighbors().get("topRight"))
                    && expectedBlackTile3.equals(blackTile.getNeighbors().get("bottomLeft"))
                    && expectedBlackTile4.equals(blackTile.getNeighbors().get("bottomRight"))
        );
    }

    @Test
    void checkNeighborsX7Y7() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.reset().getTiles()[7][7]);

        BlackTile expectedBlackTile = new BlackTile(6, 6);

        BlackTile actualBlackTile = blackTile.getNeighbors().get("topLeft");
        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
    }

    @Test
    void checkAdjacency() {
        BlackTile main = new BlackTile(0, 0);
        BlackTile neighbor = new BlackTile(1, 1);
        main.getNeighbors().put("bottomRight", neighbor);

        Assertions.assertTrue(main.isNeighbor(neighbor));
    }

    @Test
    void checkNeighborKey() {
        BlackTile main = new BlackTile(0, 0);
        BlackTile neighbor = new BlackTile(1, 1);
        main.getNeighbors().put("bottomRight", neighbor);

        Assertions.assertEquals(main.getNeighborKey(neighbor), "bottomRight");
    }

    @Test
    void checkBlackTileCasting() {
        Tile tile = new BlackTile(0, 0);
        BlackTile actualBlackTile = BlackTile.asBlackTile(tile);
        BlackTile expectedBlackTile = new BlackTile(0, 0);

        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
    }

    @Test
    void isEmptyAfterRemovingPiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        blackTile.placePiece(new BlackPiece());
        blackTile.removePiece();

        Assertions.assertTrue(blackTile.isEmpty());
    }

    @Test
    void isFullAfterPlacingPiece() {
        BlackTile blackTile = new BlackTile(0, 0);
        blackTile.placePiece(new BlackPiece());

        Assertions.assertFalse(blackTile.isEmpty());
    }

    @Test
    void checkRemovedPiece() {
        Board board = Board.reset();
        board.initPieces();

        BlackTile blackTile = BlackTile.asBlackTile(board.getTiles()[0][0]);
        Piece actualPiece = blackTile.removePiece();
        Piece expectedPiece = new BlackPiece();

        Assertions.assertEquals(expectedPiece, actualPiece);
    }

    @Test
    void getBlackTileInBetween() {
        Board board = Board.reset();
        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[0][0]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile expectedMiddleBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile actualMiddleBlackTile = blackTile1.getBlackTileInBetween(blackTile2);
        Assertions.assertEquals(expectedMiddleBlackTile, actualMiddleBlackTile);
    }

}
