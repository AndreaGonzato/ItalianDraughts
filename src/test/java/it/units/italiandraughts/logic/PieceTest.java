package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static it.units.italiandraughts.logic.BlackTileTest.addNeighbors;
import static it.units.italiandraughts.logic.BoardTest.initTiles;
import static it.units.italiandraughts.logic.BoardTest.placePieceOnTile;

public class PieceTest {

    @Test
    void getPieceType() {
        Piece piece = new Piece(PieceColor.WHITE, PieceType.MAN);

        Assertions.assertEquals(PieceType.MAN, piece.getPieceType());
    }

    @Test
    void canEatNeighborTrue(){
        Tile[][] tiles = initTiles();
        Board board = new Board(tiles);
        addNeighbors(board);

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new Piece(PieceColor.WHITE, blackTile1);
        Piece blackPiece = new Piece(PieceColor.BLACK, blackTile2);

        Assertions.assertTrue(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborFalse(){
        Tile[][] tiles = initTiles();
        Board board = new Board(tiles);
        addNeighbors(board);

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new Piece(PieceColor.WHITE, blackTile1);
        Piece blackPiece = new Piece(PieceColor.BLACK, blackTile2);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }
}
