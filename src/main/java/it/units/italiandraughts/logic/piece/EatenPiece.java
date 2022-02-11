package it.units.italiandraughts.logic.piece;

import it.units.italiandraughts.logic.tile.BlackTile;

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

    public BlackTile getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatenPiece)) return false;

        EatenPiece that = (EatenPiece) o;

        if (piece != null ? !piece.equals(that.piece) : that.piece != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = piece != null ? piece.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
