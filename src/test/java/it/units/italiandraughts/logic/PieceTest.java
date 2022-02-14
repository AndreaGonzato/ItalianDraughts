package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.*;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PieceTest {

    @Test
    void moveToNeighboringEmptyBlackTile() {
        Board board = Board.reset();

        Piece actualPiece = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(actualPiece);

        actualPiece.moveToReachableBlackTile(BlackTile.asBlackTile(board.getTiles()[1][1]));

        Assertions.assertEquals(actualPiece.getBlackTile(), BlackTile.asBlackTile(board.getTiles()[1][1]));
    }

    @Test
    void moveToReachableBlackTileByEatingPiece() {
        Board board = Board.reset();

        Piece pieceToMove = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(pieceToMove);

        Piece pieceToEat = new WhitePiece();
        BlackTile pieceToEatBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        pieceToEatBlackTile.placePiece(pieceToEat);
        EatenPiece expectedEatenPiece = new EatenPiece(pieceToEat);

        Optional<EatenPiece> actualEatenPieceOptional = pieceToMove.moveToReachableBlackTile(BlackTile.asBlackTile(board.getTiles()[2][2]));

        actualEatenPieceOptional.ifPresent(actualEatenPiece -> Assertions.assertEquals(expectedEatenPiece, actualEatenPiece));
    }

    @Test
    void move() {
        Board board = Board.reset();

        Piece actualPiece = new BlackPiece();
        BlackTile.asBlackTile(board.getTiles()[0][0]).placePiece(actualPiece);

        actualPiece.moveTo(BlackTile.asBlackTile(board.getTiles()[3][3]));

        Assertions.assertEquals(actualPiece.getBlackTile(), BlackTile.asBlackTile(board.getTiles()[3][3]));
    }

    @Test
    void getReachableNeighboringBlackTilesForWhiteMan() {
        Board board = Board.reset();

        Piece piece = new WhitePiece();
        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        whitePieceBlackTile.placePiece(piece);

        BlackTile topLeftBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile topRightBlackTile = BlackTile.asBlackTile(board.getTiles()[2][4]);

        Set<BlackTile> expectedSetBlackTiles = Set.of(topLeftBlackTile, topRightBlackTile);

        Assertions.assertEquals(piece.getReachableNeighboringBlackTiles().collect(Collectors.toSet()), expectedSetBlackTiles);
    }

    @Test
    void getReachableNeighboringBlackTilesForBlackMan() {
        Board board = Board.reset();

        Piece piece = new BlackPiece();
        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        whitePieceBlackTile.placePiece(piece);

        BlackTile bottomLeftBlackTile = BlackTile.asBlackTile(board.getTiles()[4][2]);
        BlackTile bottomRightBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);

        Set<BlackTile> expectedSetBlackTiles = Set.of(bottomLeftBlackTile, bottomRightBlackTile);

        Assertions.assertEquals(piece.getReachableNeighboringBlackTiles().collect(Collectors.toSet()), expectedSetBlackTiles);
    }

    @Test
    void getReachableNeighboringBlackTilesForKing() {
        Board board = Board.reset();

        Piece piece = new WhitePiece(PieceType.KING);
        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        whitePieceBlackTile.placePiece(piece);

        BlackTile topLeftBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile topRightBlackTile = BlackTile.asBlackTile(board.getTiles()[2][4]);
        BlackTile bottomLeftBlackTile = BlackTile.asBlackTile(board.getTiles()[4][2]);
        BlackTile bottomRightBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);

        Set<BlackTile> expectedSetBlackTiles = Set.of(topLeftBlackTile, topRightBlackTile, bottomLeftBlackTile, bottomRightBlackTile);

        Assertions.assertEquals(piece.getReachableNeighboringBlackTiles().collect(Collectors.toSet()), expectedSetBlackTiles);
    }


    @Test
    void canEatNeighborTrue() {
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        whitePieceBlackTile.placePiece(whitePiece);
        blackPieceBlackTile.placePiece(blackPiece);

        Assertions.assertTrue(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNotReachablePiece() {
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        whitePieceBlackTile.placePiece(whitePiece);
        blackPieceBlackTile.placePiece(blackPiece);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatSameColorPiece() {
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);

        Piece whitePiece1 = new WhitePiece();
        Piece whitePiece2 = new WhitePiece();
        whitePieceBlackTile.placePiece(whitePiece1);
        blackPieceBlackTile.placePiece(whitePiece2);

        Assertions.assertFalse(whitePiece1.canEatNeighbor(whitePiece2));

    }

    @Test
    void canManEatKing() {
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);

        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece(PieceType.KING);
        whitePieceBlackTile.placePiece(whitePiece);
        blackPieceBlackTile.placePiece(blackPiece);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborWithFullLandingTile() {
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile blackPieceDefenderBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);


        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        Piece blackPieceDefender = new BlackPiece();
        whitePieceBlackTile.placePiece(whitePiece);
        blackPieceBlackTile.placePiece(blackPiece);
        blackPieceDefenderBlackTile.placePiece(blackPieceDefender);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborWhenLandingTileDoesNotExist() {
        // when landing tile is outside the board
        Board board = Board.reset();

        BlackTile whitePieceBlackTile = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackPieceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][0]);


        Piece whitePiece = new WhitePiece();
        Piece blackPiece = new BlackPiece();
        whitePieceBlackTile.placePiece(whitePiece);
        blackPieceBlackTile.placePiece(blackPiece);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void getPositionAfterEatingNeighbor() {
        Board board = Board.reset();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[0][0]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile expectedDestination = BlackTile.asBlackTile(board.getTiles()[2][2]);
        Piece eater = new BlackPiece();
        Piece eatee = new WhitePiece();
        source.placePiece(eater);
        over.placePiece(eatee);
        BlackTile actualDestination = eater.getPositionAfterEatingNeighbor(eatee);
        Assertions.assertEquals(actualDestination, expectedDestination);
    }

}
