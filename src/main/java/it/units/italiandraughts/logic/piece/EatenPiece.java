package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.logic.tile.BlackTile;

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

    public void restore() {
        position.placePiece(piece);
    }

    public Piece getPiece() {
        return piece;
    }

    public BlackTile getPosition() {
        return position;
    }

}
