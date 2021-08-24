package it.units.italiandraughts.logic;


import it.units.italiandraughts.ui.PieceType;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.beans.PropertyChangeSupport;

public class Board {
    private final LogicTile[][] board;
    public static final int SIZE = 8;
    private PropertyChangeSupport support;

    public Board() {
        support = new PropertyChangeSupport(this);
        board = new LogicTile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                LogicTile square = new LogicTile(row, col);
                board[row][col] = square;
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = row % 2; col < SIZE; col += 2) {
                if (row < 3) {
                    board[row][col].placePiece(new Piece(PieceType.PLAYER2, col, row));
                } else if (row > 4) {
                    board[row][col].placePiece(new Piece(PieceType.PLAYER1, col, row));
                }
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    // TODO this has to be re-implemented
    public void move(Piece piece, int toX, int toY){
        //board[piece.getY()][piece.getX()] = null;
        //piece.move(toX, toY);
        //board[toY][toX] = piece;
        //notifyChange();
    }

    private void notifyChange() {
        support.firePropertyChange("board", null, board);
    }

    public void init() {
        support.firePropertyChange("boardinit", null, board);
        notifyChange();
    }

    public void empty(){
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

    public LogicTile[][] getBoard() {
        return board;
    }
}
