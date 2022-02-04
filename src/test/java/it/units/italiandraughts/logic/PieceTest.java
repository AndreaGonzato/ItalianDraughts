package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.units.italiandraughts.logic.BlackTileTest.addNeighbors;
import static it.units.italiandraughts.logic.BoardTest.initTiles;

public class PieceTest {


    @Test
    void canEatNeighborTrue(){
        Tile[][] tiles = initTiles();
        Board board = Board.getBoard();
        board.setTiles(tiles);
        addNeighbors(board);

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        Assertions.assertTrue(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborFalse(){
        Tile[][] tiles = initTiles();
        Board board = Board.getBoard();
        board.setTiles(tiles);
        addNeighbors(board);

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void eatNeighbor(){
        Tile[][] tiles = initTiles();
        Board board = Board.getBoard();
        board.setTiles(tiles);
        addNeighbors(board);

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[2][2]);


        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        whitePiece.eatNeighbor(blackPiece);

        // TODO need to write code to pass this assertion
        Assertions.assertNull(blackTile1.getPiece());
        Assertions.assertNull(blackTile2.getPiece());
        Assertions.assertEquals(whitePiece, blackTile3.getPiece());
    }
}
