package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.EventType;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.InvalidPlayersException;
import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
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
    void firstActivePlayer(){
        Player whitePlayer = new Player("Player1", PieceColor.WHITE);
        Player blackPlayer = new Player("Player1", PieceColor.BLACK);

        Game game = new Game(blackPlayer, whitePlayer);
        Player activePlayer = game.getActivePlayer();

        Assertions.assertEquals(whitePlayer, activePlayer);
    }

    @Test
    void thatTwoPayerCanNotHaveTheSamePieceColor(){
        Player whitePlayer1 = new Player("Player1", PieceColor.WHITE);
        Player whitePlayer2 = new Player("Player2", PieceColor.WHITE);

        boolean thereIsAnException = false;
        try {
            new Game(whitePlayer1, whitePlayer2);
        }catch (InvalidPlayersException e){
            thereIsAnException = true;
        }

        Assertions.assertTrue(thereIsAnException);
    }

    @Test
    void makeAndSaveMoveWithSimpleMove() {
        Board board = Board.reset();
        board.initPieces();

        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));

        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Piece piece = sourceBlackTile.getPiece();
        Move expectedMove = new Move(piece, List.of(sourceBlackTile, destinationBlackTile));
        expectedMove.make();
        expectedMove.undo();
        Move actualMove = game.makeAndSaveMove(piece, List.of(sourceBlackTile, destinationBlackTile));

        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void checkThatMakeAndSaveMoveAddTheMoveInList() {
        Board board = Board.reset();
        board.initPieces();

        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));

        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Piece piece = sourceBlackTile.getPiece();
        Move expectedMove = new Move(piece, List.of(sourceBlackTile, destinationBlackTile));
        expectedMove.make();
        expectedMove.undo();
        int movesSizeBefore = game.getMoves().size();
        game.makeAndSaveMove(piece, List.of(sourceBlackTile, destinationBlackTile));
        int movesSizeAfter = game.getMoves().size();

        Assertions.assertEquals(1, movesSizeAfter-movesSizeBefore);
    }

    @Test
    void makeAndSaveMoveEatingOnePiece() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Board board = Board.reset();
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

    @Test
    void makeAndSaveMoveEatingTwoPieces() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Board board = Board.reset();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[0][0]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][0]);
        BlackTile intermediate = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile over1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile over2 = BlackTile.asBlackTile(board.getTiles()[3][1]);
        source.placePiece(new BlackPiece(PieceType.KING));
        over1.placePiece(new WhitePiece());
        over2.placePiece(new WhitePiece());
        Move expectedMove = new Move(source.getPiece(), List.of(source, intermediate, destination));
        expectedMove.make();
        expectedMove.undo();
        game.makeAndSaveMove(source.getPiece(), List.of(source, intermediate, destination));
        Move actualMove = game.getMoves().get(0);
        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void moveActivePieceTo() {
        Board board = Board.reset();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile simpleMoveDestination = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[4][6]);
        BlackTile eatingDestination = BlackTile.asBlackTile(board.getTiles()[3][7]);
        source.placePiece(new WhitePiece());
        over.placePiece(new BlackPiece());
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        game.addListeners(EventType.GAME_OVER);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        Player formerActivePlayer = game.getActivePlayer();
        game.setActiveTile(source);
        game.moveActivePieceTo(eatingDestination);
        Assertions.assertTrue(
                source.isEmpty() && simpleMoveDestination.isEmpty() && !eatingDestination.isEmpty()
                && over.isEmpty() && !game.getActivePlayer().equals(formerActivePlayer)
        );
    }

    @Test
    void undoLastMoveFail() {
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        boolean caught = false;
        try {
            game.undoLastMove();
        } catch (IllegalButtonClickException e) {
            caught = true;
        }
        Assertions.assertTrue(caught);
    }

    @Test
    void undoLastSimpleMove() {
        Board board = Board.reset();
        board.initPieces();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][4]);
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        game.addListeners(EventType.GAME_OVER);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        game.undoLastMove();
        Assertions.assertTrue(!source.isEmpty() && destination.isEmpty());
    }

    @Test
    void undoLastEatingMove() {
        Board board = Board.getBoard();
        board.initPieces();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        over.placePiece(new BlackPiece());
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        game.addListeners(EventType.GAME_OVER);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        game.undoLastMove();
        Assertions.assertTrue(!source.isEmpty() && destination.isEmpty() && !over.isEmpty());
    }

    @Test
    void undo() {
        Board board = Board.reset();
        board.initPieces();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        over.placePiece(new BlackPiece());
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        game.addListeners(EventType.GAME_OVER);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        Player formerActivePlayer = game.getActivePlayer();
        game.undo();
        Assertions.assertTrue(
                !source.isEmpty() && destination.isEmpty() && !over.isEmpty()
                && !formerActivePlayer.equals(game.getActivePlayer())
        );
    }

}
