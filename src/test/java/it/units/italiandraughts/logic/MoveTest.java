package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveTest {

    @Test
    void move() {
        Board board = Board.reset();
        board.removePieces();

        Piece piece = new WhitePiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[0][0]);//new BlackTile(0, 0);
        startingBlackTile.placePiece(piece);

        BlackTile firstStepBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);;

        Move move = new Move(piece, List.of(startingBlackTile, firstStepBlackTile));
        move.make();

        // TODO check other things...
        Assertions.assertTrue(startingBlackTile.isEmpty());
    }
}
