package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveTest {

    @Test
    void checkMakeWithSimpleMove() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);

        Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
        move.make();

        Assertions.assertTrue(startingBlackTile.isEmpty()
                && !targetBlackTile.isEmpty()
                && targetBlackTile.getPiece().equals(piece));
    }

    @Test
    void checkMakeWithSingleEating() {
        Board board = Board.reset();

        Piece movingPiece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(movingPiece);

        Piece eatenPiece = new WhitePiece();
        BlackTile eatenPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        eatenPieceBlackTile.placePiece(eatenPiece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Move move = new Move(movingPiece, List.of(startingBlackTile, targetBlackTile));
        move.make();

        Assertions.assertTrue(startingBlackTile.isEmpty()
                && !targetBlackTile.isEmpty()
                && targetBlackTile.getPiece().equals(movingPiece)
                && eatenPieceBlackTile.isEmpty());
    }


    @Test
    void checkMakeWithDoubleEating() {
        Board board = Board.reset();

        Piece movingPiece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(movingPiece);

        Piece eatingPiece1 = new WhitePiece();
        BlackTile eatingPiece1BlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        eatingPiece1BlackTile.placePiece(eatingPiece1);

        Piece eatingPiece2 = new WhitePiece();
        BlackTile eatingPiece2BlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);
        eatingPiece2BlackTile.placePiece(eatingPiece2);


        BlackTile intermediateBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);

        Move move = new Move(movingPiece, List.of(startingBlackTile, intermediateBlackTile, targetBlackTile));
        move.make();

        Assertions.assertTrue(startingBlackTile.isEmpty()
                && !targetBlackTile.isEmpty()
                && targetBlackTile.getPiece().equals(movingPiece)
                && eatingPiece1BlackTile.isEmpty()
                && eatingPiece2BlackTile.isEmpty());
    }

    @Test
    void tryMovingAWhiteManDownwards() {
        Board board = Board.reset();

        Piece piece = new WhitePiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        });

    }

    @Test
    void tryMovingABlackManUpwards() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        });

    }

    @Test
    void tryMakingAnExcessivelyLongStep() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        });
    }

    @Test
    void checkTileOccupationAfterUndoing() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        targetBlackTile.placePiece(piece);

        Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
        move.undo();

        Assertions.assertTrue(!startingBlackTile.isEmpty()
                && targetBlackTile.isEmpty()
                && startingBlackTile.getPiece().equals(piece));
    }

    @Test
    void checkEatenPieceRestorationAfterUndoing() {
        Board board = Board.reset();

        Piece pieceMoved = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(pieceMoved);

        Piece eatenPiece = new WhitePiece();
        BlackTile eatenPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        eatenPieceBlackTile.placePiece(eatenPiece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Move move = new Move(pieceMoved, List.of(startingBlackTile, targetBlackTile));
        move.undo();

        Assertions.assertTrue(!eatenPieceBlackTile.isEmpty()
                && eatenPieceBlackTile.getPiece().equals(eatenPiece));
    }


    @Test
    void checkEatenPiecesRestorationAfterUndoing() {
        Board board = Board.reset();

        Piece pieceMoved = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(pieceMoved);

        Piece eatenPiece1 = new WhitePiece();
        BlackTile eatenPiece1BlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        eatenPiece1BlackTile.placePiece(eatenPiece1);

        BlackTile acrossBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece eatenPiece2 = new WhitePiece();
        BlackTile eatenPiece2BlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);
        eatenPiece2BlackTile.placePiece(eatenPiece2);

        BlackTile arrivedBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);

        Move move = new Move(pieceMoved, List.of(startingBlackTile, acrossBlackTile, arrivedBlackTile));
        move.undo();

        Assertions.assertTrue(!eatenPiece1BlackTile.isEmpty()
                && eatenPiece1BlackTile.getPiece().equals(eatenPiece1)
                && !eatenPiece2BlackTile.isEmpty()
                && eatenPiece2BlackTile.getPiece().equals(eatenPiece2));
    }

    @Test
    void checkDemotionAfterUndoing() {
        Board board = Board.reset();

        Piece piece = new WhitePiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[0][2]);
        startingBlackTile.placePiece(piece);

        Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
        move.make();
        move.undo();

        Assertions.assertTrue(move.hasPromoted() && piece.getPieceType().equals(PieceType.MAN));
    }

}
