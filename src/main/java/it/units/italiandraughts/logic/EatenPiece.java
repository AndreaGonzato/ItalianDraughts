package it.units.italiandraughts.logic;

public class EatenPiece {

    private final Piece piece;
    private final BlackTile position;

    EatenPiece(Piece piece, BlackTile position) {
        this.piece = piece;
        this.position = position;
    }

    EatenPiece(BlackTile blackTile) {
        this.piece = blackTile.getPiece();
        this.position = blackTile;
    }

    void restore() {
        position.placePiece(piece);
    }

    public Piece getPiece() {
        return piece;
    }

    public BlackTile getPosition() {
        return position;
    }

}
