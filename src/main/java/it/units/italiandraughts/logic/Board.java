package it.units.italiandraughts.logic;


import it.units.italiandraughts.exception.IllegalMovePieceException;
import it.units.italiandraughts.ui.PieceType;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.beans.PropertyChangeSupport;

public class Board {
    private final LogicTile[][] tiles;
    public static final int SIZE = 8;
    private PropertyChangeSupport support;

    public Board() {
        support = new PropertyChangeSupport(this);
        tiles = new LogicTile[SIZE][SIZE];

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                LogicTile square = new LogicTile(row, col);
                tiles[row][col] = square;
            }
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = row % 2; col < SIZE; col += 2) {
                if (row < 3) {
                    tiles[row][col].placePiece(new Piece(PieceType.PLAYER2, col, row));
                } else if (row > 4) {
                    tiles[row][col].placePiece(new Piece(PieceType.PLAYER1, col, row));
                }
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void move(int fromX, int fromY, int toX, int toY){
        if ((toX + toY) % 2 == 1){
            throw new IllegalMovePieceException("The required move is illegal because no pieces can stand on a white tile");
        }
        Piece piece = tiles[fromY][fromX].getPiece();
        tiles[fromX][fromY].placePiece(null);
        piece.setX(toX);
        piece.setY(toY);
        tiles[toX][toY].placePiece(piece);
        notifyChange();
    }

    public void notifyChange() {
        support.firePropertyChange("board", null, tiles);
    }


    public void empty(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j <SIZE ; j++) {
                tiles[i][j] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Board{ board=\n");
        for (int i = 0; i < SIZE; i++) {
            result.append(Arrays.toString(tiles[i])).append("\n");
        }
        result.append(" }");
        return result.toString();
    }

    public LogicTile[][] getTiles() {
        return tiles;
    }
}
