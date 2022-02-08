package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.List;

@ExtendWith(ApplicationExtension.class)
public class GameTest {

    @Test
    void firstActivePlayerPieceColor(){
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Player activePlayer = game.getActivePlayer();

        Assertions.assertEquals(activePlayer.getPieceColor(), PieceColor.WHITE);
    }

    @Test
    void moveAndLog() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Piece piece = new BlackPiece();
        BlackTile source = new BlackTile(2, 2);
        source.placePiece(piece);
        BlackTile destination = new BlackTile(3, 3);
        Move expectedMove = new Move(piece, List.of(destination));
        expectedMove.make();
        Board board = Board.reset();
        BlackTile actualSource = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile actualDestination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Move actualMove = game.moveAndLog(actualSource.getPiece(), List.of(actualDestination));

        Assertions.assertEquals(expectedMove, actualMove);
    }

}
