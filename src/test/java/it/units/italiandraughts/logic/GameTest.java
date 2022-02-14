package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
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
    void makeAndSaveMoveWithSimpleMove() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Board board = Board.reset();
        board.initPieces();

        BlackTile source = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Move expectedMove = new Move(source.getPiece(), List.of(source, destination));
        expectedMove.make();
        expectedMove.undo();
        game.makeAndSaveMove(source.getPiece(), List.of(source, destination));
        Move actualMove = game.getMoves().get(0);

        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void makeAndSaveMoveWithOneEating() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Board board = Board.reset();
        board.removePieces();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[3][3]);
        source.placePiece(new BlackPiece());
        over.placePiece(new WhitePiece());
        Move expectedMove = new Move(source.getPiece(), List.of(source, destination));
        expectedMove.make();
        expectedMove.undo();
        game.makeAndSaveMove(source.getPiece(), List.of(source, destination));
        Move actualMove = game.getMoves().get(0);
        Assertions.assertEquals(expectedMove, actualMove);
    }

}
