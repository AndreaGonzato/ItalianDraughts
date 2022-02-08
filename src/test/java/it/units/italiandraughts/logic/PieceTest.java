package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.EatenPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PieceTest {

    @Test
    void moveToReachableNearEmptyBlackTile(){
        Board board = Board.reset();
        board.removePieces();

        Piece actualPiece = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(actualPiece);

        actualPiece.moveToReachableBlackTile(BlackTile.asBlackTile(board.getTiles()[1][1]));

        Assertions.assertEquals(actualPiece.getBlackTile(), BlackTile.asBlackTile(board.getTiles()[1][1]));
    }

    @Test
    void moveToReachableBlackTileByEatingPiece(){
        Board board = Board.reset();
        board.removePieces();

        Piece pieceToMove = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(pieceToMove);

        Piece pieceToEat = new WhitePiece();
        BlackTile.asBlackTile(board.getTiles()[1][1]).placePiece(pieceToEat);
        EatenPiece eatenPiece = new EatenPiece(pieceToEat, BlackTile.asBlackTile(board.getTiles()[1][1]));

        Optional<EatenPiece> actualEatenPiece = pieceToMove.moveToReachableBlackTile(BlackTile.asBlackTile(board.getTiles()[2][2]));

        Assertions.assertEquals(actualEatenPiece.get(), eatenPiece);
    }

    @Test
    void move(){
        Board board = Board.reset();
        board.removePieces();

        Piece actualPiece = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(actualPiece);

        actualPiece.move(BlackTile.asBlackTile(board.getTiles()[3][3]));

        Assertions.assertEquals(actualPiece.getBlackTile(), BlackTile.asBlackTile(board.getTiles()[3][3]));
    }

    @Test
    void canEatNeighborTrue(){
        Board board = Board.reset();
        board.removePieces();

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        blackTile1.placePiece(whitePiece);
        blackTile2.placePiece(blackPiece);

        Assertions.assertTrue(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborFalse(){
        Board board = Board.reset();
        board.removePieces();

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        blackTile1.placePiece(whitePiece);
        blackTile2.placePiece(blackPiece);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void eatNeighbor(){
        Board board = Board.reset();
        board.removePieces();

        BlackTile fromBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile overBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        BlackTile toBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);


        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        fromBlackTile.placePiece(whitePiece);
        overBlackTile.placePiece(blackPiece);

        whitePiece.eatNeighbor(blackPiece);

        Assertions.assertTrue(
                fromBlackTile.getPiece() == null
                        && overBlackTile.getPiece() == null
                        && whitePiece.equals(toBlackTile.getPiece())
        );
    }
}
