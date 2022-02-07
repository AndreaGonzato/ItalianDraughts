package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlackTileTest {

    @Test
    void checkNeighborsX0Y0() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.getBoard().getTiles()[0][0]);

        BlackTile expectedBlackTile = new BlackTile(1, 1);
        new BlackPiece(expectedBlackTile);

        BlackTile actualBlackTile = blackTile.getNeighbors().get("bottomRight");
        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
    }

    @Test
    void checkNeighborsX1Y1() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.getBoard().getTiles()[1][1]);

        BlackTile expectedBlackTile1 = new BlackTile(0, 0);
        new BlackPiece(expectedBlackTile1);
        BlackTile expectedBlackTile2 = new BlackTile(2, 0);
        new BlackPiece(expectedBlackTile2);
        BlackTile expectedBlackTile3 = new BlackTile(0, 2);
        new BlackPiece(expectedBlackTile3);
        BlackTile expectedBlackTile4 = new BlackTile(2, 2);
        new BlackPiece(expectedBlackTile4);

        Assertions.assertTrue(expectedBlackTile1.equals(blackTile.getNeighbors().get("topLeft"))
                    && expectedBlackTile2.equals(blackTile.getNeighbors().get("topRight"))
                    && expectedBlackTile3.equals(blackTile.getNeighbors().get("bottomLeft"))
                    && expectedBlackTile4.equals(blackTile.getNeighbors().get("bottomRight"))
        );
    }

    @Test
    void checkNeighborsX7Y7() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.getBoard().getTiles()[7][7]);

        BlackTile expectedBlackTile = new BlackTile(6, 6);
        new WhitePiece(expectedBlackTile);

        BlackTile actualBlackTile = blackTile.getNeighbors().get("topLeft");
        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
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
        BlackTile blackTile = BlackTile.asBlackTile(Board.getBoard().getTiles()[0][0]);
        blackTile.removePiece();

        Piece expectedPiece = new BlackPiece(blackTile);

        Assertions.assertEquals(expectedPiece, blackTile.removePiece());
    }

}
