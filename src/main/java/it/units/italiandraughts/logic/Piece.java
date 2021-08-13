package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalMovePieceException;
import it.units.italiandraughts.ui.PieceType;

public class Piece {
    private final PieceType pieceType;
    private int x;
    private int y;

    public Piece(PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
    }

    public void move(int toX, int toY){
        if ((toX + toY) % 2 == 1){
            throw new IllegalMovePieceException("The required move is illegal because no pieces can stand on a white tile");
        }
        x = toX;
        y = toY;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

        piece = (Piece) o;

        return pieceType == piece.pieceType;
    }

    @Override
    public String toString() {
        return pieceType.toString();
    }

    @Override
    public int hashCode() {
        return pieceType.hashCode();
    }
}
