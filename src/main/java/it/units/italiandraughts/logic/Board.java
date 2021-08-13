package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.BoardDrawer;
import it.units.italiandraughts.ui.PieceType;

import java.util.Arrays;

public class Board {
    private final Piece[][] board;
    public static final int SIZE = 8;

    public Board() {
        board = new Piece[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = row % 2; col < SIZE; col += 2) {
                if (row < 3) {
                    board[row][col] = new Piece(PieceType.PLAYER2, col, row);
                } else if (row > 4) {
                    board[row][col] = new Piece(PieceType.PLAYER1, col, row);
                }
            }
        }
    }

    public void draw(BoardDrawer boardDrawer) {
        boardDrawer.draw(board);
    }

    public void move(Piece piece, int toX, int toY){
        board[piece.getY()][piece.getX()] = null;
        piece.move(toX, toY);
        board[toY][toX] = piece;
    }

    public void empty(){
        // TODO use functional programming
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE ; j++) {
                board[i][j] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Board{ board=\n");
        for (int i = 0; i < SIZE; i++) {
            result.append(Arrays.toString(board[i])).append("\n");
        }
        result.append(" }");
        return result.toString();
    }

    public Piece[][] getBoard() {
        return board;
    }
}
