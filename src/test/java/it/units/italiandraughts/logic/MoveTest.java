package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveTest {

    @Test
    void makeMoveToNeighbor() {
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
    void makeMoveAndEatPiece() {
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
    void makeMoveWith2Jumps() {
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


        BlackTile acrossBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        ;

        Move move = new Move(movingPiece, List.of(startingBlackTile, acrossBlackTile, targetBlackTile));
        move.make();

        Assertions.assertTrue(startingBlackTile.isEmpty()
                && !targetBlackTile.isEmpty()
                && targetBlackTile.getPiece().equals(movingPiece)
                && eatingPiece1BlackTile.isEmpty()
                && eatingPiece2BlackTile.isEmpty());
    }

    @Test
    void tryMakeIllegalMoveWhiteManMoveDown() {
        Board board = Board.reset();

        Piece piece = new WhitePiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        ;

        boolean isIllegal = false;
        try {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        } catch (IllegalArgumentException e) {
            isIllegal = true;
        }

        Assertions.assertTrue(isIllegal);
    }

    @Test
    void tryMakeIllegalMoveBlackManMoveUp() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);
        ;

        boolean isIllegal = false;
        try {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        } catch (IllegalArgumentException e) {
            isIllegal = true;
        }

        Assertions.assertTrue(isIllegal);
    }

    @Test
    void tryMakeIllegalMoveBigStepWithDistanceJumpBiggerThan2() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        startingBlackTile.placePiece(piece);

        BlackTile targetBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        ;

        boolean isIllegal = false;
        try {
            Move move = new Move(piece, List.of(startingBlackTile, targetBlackTile));
            move.make();
        } catch (IllegalArgumentException e) {
            isIllegal = true;
        }

        Assertions.assertTrue(startingBlackTile.calculateDistance(targetBlackTile) > 2 && isIllegal);
    }

    @Test
    void undoSimpleMove() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile startingBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile arrivedBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        arrivedBlackTile.placePiece(piece);

        Move move = new Move(piece, List.of(startingBlackTile, arrivedBlackTile));
        move.undo();

        Assertions.assertTrue(!startingBlackTile.isEmpty()
                && arrivedBlackTile.isEmpty()
                && startingBlackTile.getPiece().equals(piece));
    }

}
