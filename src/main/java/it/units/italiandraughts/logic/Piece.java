package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

public class Piece {
    private final PieceColor pieceColor;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public PieceColor getPieceType() {
        return pieceColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

        return pieceColor == piece.pieceColor;
    }

    @Override
    public String toString() {
        return pieceColor.toString();
    }

    @Override
    public int hashCode() {
        return pieceColor.hashCode();
    }
}
