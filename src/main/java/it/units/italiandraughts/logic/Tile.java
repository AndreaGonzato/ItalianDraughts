package it.units.italiandraughts.logic;

public class Tile {

    private final int x;
    private final int y;
    private Piece piece;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
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

    public Piece getPiece() {
        return piece;
    }
}
