package it.units.italiandraughts.logic;

public class Piece {
    private PieceType pieceType;

    public Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;

        Piece piece = (Piece) o;

        return pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return pieceType.hashCode();
    }
}
