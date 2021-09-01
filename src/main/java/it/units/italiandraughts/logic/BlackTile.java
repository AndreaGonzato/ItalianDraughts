package it.units.italiandraughts.logic;

public class BlackTile extends Tile{

    private Piece piece;

    BlackTile(int x, int y){
        super(x, y);
    }

    BlackTile(int x, int y, Piece piece){
        super(x, y);
        this.piece = piece;
    }

    public static BlackTile asBlackTile(Tile tile){
        return (BlackTile) tile;
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
}
