package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlackTileTest {

    @Test
    void getPiece(){
        BlackTile blackTile = new BlackTile(0,0);

        Piece expectedPiece = new Piece(PieceColor.WHITE, blackTile);
        Piece actualPiece = blackTile.getPiece();

        Assertions.assertEquals(expectedPiece, actualPiece);

    }

}
