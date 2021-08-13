package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void initialization() {
        Piece[][] expectedPieces = setUpBoard();

        Board board = new Board();

        Assertions.assertArrayEquals(expectedPieces, board.getBoard());
    }

    @Test
    void empty() {
        Piece[][] expectedPieces = new Piece[8][8];

        Board board = new Board();
        board.empty();

        Assertions.assertArrayEquals(expectedPieces, board.getBoard());
    }

    @Test
    void moveTopRightPiecePlayer1ToLeft() {
        Piece[][] expectedBoard = setUpBoard();
        expectedBoard[4][6] = expectedBoard[5][7];
        expectedBoard[5][7] = null;

        Board board = new Board();
        board.move(board.getBoard()[5][7], 6, 4);

        Assertions.assertArrayEquals(expectedBoard, board.getBoard());
    }

    private Piece[][] setUpBoard() {
        Piece[][] pieces = new Piece[8][8];
        addPiece(pieces, PieceType.PLAYER2, 0, 0);
        addPiece(pieces, PieceType.PLAYER2, 2, 0);
        addPiece(pieces, PieceType.PLAYER2, 4, 0);
        addPiece(pieces, PieceType.PLAYER2, 6, 0);

        addPiece(pieces, PieceType.PLAYER2, 1, 1);
        addPiece(pieces, PieceType.PLAYER2, 3, 1);
        addPiece(pieces, PieceType.PLAYER2, 5, 1);
        addPiece(pieces, PieceType.PLAYER2, 7, 1);

        addPiece(pieces, PieceType.PLAYER2, 0, 2);
        addPiece(pieces, PieceType.PLAYER2, 2, 2);
        addPiece(pieces, PieceType.PLAYER2, 4, 2);
        addPiece(pieces, PieceType.PLAYER2, 6, 2);

        addPiece(pieces, PieceType.PLAYER1, 1, 5);
        addPiece(pieces, PieceType.PLAYER1, 3, 5);
        addPiece(pieces, PieceType.PLAYER1, 5, 5);
        addPiece(pieces, PieceType.PLAYER1, 7, 5);

        addPiece(pieces, PieceType.PLAYER1, 0, 6);
        addPiece(pieces, PieceType.PLAYER1, 2, 6);
        addPiece(pieces, PieceType.PLAYER1, 4, 6);
        addPiece(pieces, PieceType.PLAYER1, 6, 6);

        addPiece(pieces, PieceType.PLAYER1, 1, 7);
        addPiece(pieces, PieceType.PLAYER1, 3, 7);
        addPiece(pieces, PieceType.PLAYER1, 5, 7);
        addPiece(pieces, PieceType.PLAYER1, 7, 7);
        return pieces;
    }

    private void addPiece(Piece[][] pieces, PieceType pieceType, int x, int y) {
        Piece piece = new Piece(pieceType, x, y);
        pieces[y][x] = piece;
    }
}
