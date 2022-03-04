package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.logic.tile.BlackTile;

import java.util.Objects;

public class EatenPiece {

    private final Piece piece;
    private final BlackTile position;

    public EatenPiece(Piece piece, BlackTile position) {
        this.piece = piece;
        this.position = position;
        piece.setBlackTile(position);
    }

    public EatenPiece(Piece piece) {
        this.piece = piece;
        this.position = piece.getBlackTile();
    }

    public void restore() {
        position.placePiece(piece);
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatenPiece that)) return false;

        if (!Objects.equals(piece, that.piece)) return false;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = piece != null ? piece.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

}
