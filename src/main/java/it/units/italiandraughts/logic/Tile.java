package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.Square;

public class Tile {

    private final int x;
    private final int y;
    private Piece piece;
    private Square square;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isEmpty() {
        return piece == null;
    }

    void placePiece(Piece piece) {
        this.piece = piece;
    }


    void removePiece() {
        this.piece = null;
    }

    public Piece getPiece() {
        return piece;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (x != tile.x) return false;
        if (y != tile.y) return false;
        return piece != null ? piece.equals(tile.piece) : tile.piece == null;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (piece != null ? piece.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", piece=" + piece +
                '}';
    }
}
