package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void getPieceType() {
        Piece piece = new Piece(PieceColor.WHITE, PieceType.MAN);

        Assertions.assertEquals(PieceType.MAN, piece.getPieceType());
    }
}
