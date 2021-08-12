package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void initializationPosition() {
        Board board = new Board();
        Piece[][] pieces = new Piece[8][8];
        pieces[0][0] = new Piece(PieceType.PLAYER2);
        pieces[0][2] = new Piece(PieceType.PLAYER2);
        pieces[0][4] = new Piece(PieceType.PLAYER2);
        pieces[0][6] = new Piece(PieceType.PLAYER2);
        pieces[1][1] = new Piece(PieceType.PLAYER2);
        pieces[1][3] = new Piece(PieceType.PLAYER2);
        pieces[1][5] = new Piece(PieceType.PLAYER2);
        pieces[1][7] = new Piece(PieceType.PLAYER2);
        pieces[2][0] = new Piece(PieceType.PLAYER2);
        pieces[2][2] = new Piece(PieceType.PLAYER2);
        pieces[2][4] = new Piece(PieceType.PLAYER2);
        pieces[2][6] = new Piece(PieceType.PLAYER2);

        pieces[5][1] = new Piece(PieceType.PLAYER1);
        pieces[5][3] = new Piece(PieceType.PLAYER1);
        pieces[5][5] = new Piece(PieceType.PLAYER1);
        pieces[5][7] = new Piece(PieceType.PLAYER1);
        pieces[6][0] = new Piece(PieceType.PLAYER1);
        pieces[6][2] = new Piece(PieceType.PLAYER1);
        pieces[6][4] = new Piece(PieceType.PLAYER1);
        pieces[6][6] = new Piece(PieceType.PLAYER1);
        pieces[7][1] = new Piece(PieceType.PLAYER1);
        pieces[7][3] = new Piece(PieceType.PLAYER1);
        pieces[7][5] = new Piece(PieceType.PLAYER1);
        pieces[7][7] = new Piece(PieceType.PLAYER1);

        Assertions.assertArrayEquals(pieces, board.getBoard());
    }
}
