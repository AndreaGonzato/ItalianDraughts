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
        BlackTile blackTile = BlackTile.asBlackTile(Board.reset().getTiles()[0][0]);

        BlackTile expectedBlackTile = new BlackTile(1, 1);
        expectedBlackTile.placePiece(new BlackPiece());

        BlackTile actualBlackTile = blackTile.getNeighbors().get("bottomRight");
        Assertions.assertEquals(expectedBlackTile, actualBlackTile);
    }

    @Test
    void checkNeighborsX1Y1() {
        BlackTile blackTile = BlackTile.asBlackTile(Board.reset().getTiles()[1][1]);

        BlackTile expectedBlackTile1 = new BlackTile(0, 0);
        expectedBlackTile1.placePiece(new BlackPiece());
        BlackTile expectedBlackTile2 = new BlackTile(2, 0);
        expectedBlackTile2.placePiece(new BlackPiece());
        BlackTile expectedBlackTile3 = new BlackTile(0, 2);
        expectedBlackTile3.placePiece(new BlackPiece());
        BlackTile expectedBlackTile4 = new BlackTile(2, 2);
        expectedBlackTile4.placePiece(new BlackPiece());

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
        expectedBlackTile.placePiece(new WhitePiece());

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
        BlackTile blackTile = BlackTile.asBlackTile(Board.getBoard().getTiles()[0][0]);
        Piece actualPiece = blackTile.removePiece();
        Piece expectedPiece = new BlackPiece();

        Assertions.assertEquals(expectedPiece, actualPiece);
    }

}
