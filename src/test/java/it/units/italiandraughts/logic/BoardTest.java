package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void initializationPosition() {
        Board board = new Board();
        Piece[][] pieces = new Piece[8][8];
        pieces[0][0] = new Piece(PieceType.PLAYER2, 0, 0);
        pieces[0][2] = new Piece(PieceType.PLAYER2, 2, 0);
        pieces[0][4] = new Piece(PieceType.PLAYER2, 4, 0);
        pieces[0][6] = new Piece(PieceType.PLAYER2, 6, 0);
        pieces[1][1] = new Piece(PieceType.PLAYER2, 1, 1);
        pieces[1][3] = new Piece(PieceType.PLAYER2, 3, 1);
        pieces[1][5] = new Piece(PieceType.PLAYER2, 5, 1);
        pieces[1][7] = new Piece(PieceType.PLAYER2, 7, 1);
        pieces[2][0] = new Piece(PieceType.PLAYER2, 0, 2);
        pieces[2][2] = new Piece(PieceType.PLAYER2, 2, 2);
        pieces[2][4] = new Piece(PieceType.PLAYER2, 4, 2);
        pieces[2][6] = new Piece(PieceType.PLAYER2, 6, 2);

        pieces[5][1] = new Piece(PieceType.PLAYER1,1,5);
        pieces[5][3] = new Piece(PieceType.PLAYER1,3,5);
        pieces[5][5] = new Piece(PieceType.PLAYER1,5,5);
        pieces[5][7] = new Piece(PieceType.PLAYER1,7,5);
        pieces[6][0] = new Piece(PieceType.PLAYER1,0,6);
        pieces[6][2] = new Piece(PieceType.PLAYER1,2,6);
        pieces[6][4] = new Piece(PieceType.PLAYER1,4,6);
        pieces[6][6] = new Piece(PieceType.PLAYER1,6,6);
        pieces[7][1] = new Piece(PieceType.PLAYER1,1,7);
        pieces[7][3] = new Piece(PieceType.PLAYER1,3,7);
        pieces[7][5] = new Piece(PieceType.PLAYER1, 5, 7);
        pieces[7][7] = new Piece(PieceType.PLAYER1, 7, 7);

        Assertions.assertArrayEquals(pieces, board.getBoard());
    }
}
