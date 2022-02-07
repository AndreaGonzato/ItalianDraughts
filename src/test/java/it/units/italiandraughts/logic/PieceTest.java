package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void canEatNeighborTrue(){
        Board board = Board.getBoard();
        board.removePieces();

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        Assertions.assertTrue(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void canEatNeighborFalse(){
        Board board = Board.getBoard();
        board.removePieces();

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);

        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        Assertions.assertFalse(whitePiece.canEatNeighbor(blackPiece));

    }

    @Test
    void eatNeighbor(){
        Board board = Board.getBoard();
        board.removePieces();

        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[3][3]);
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[2][2]);


        Piece whitePiece = new WhitePiece(blackTile1);
        Piece blackPiece = new BlackPiece(blackTile2);

        whitePiece.eatNeighbor(blackPiece);

        Assertions.assertTrue(
                blackTile1.getPiece() == null
                        && blackTile2.getPiece() == null
                        && whitePiece.equals(blackTile3.getPiece())
        );
    }
}
