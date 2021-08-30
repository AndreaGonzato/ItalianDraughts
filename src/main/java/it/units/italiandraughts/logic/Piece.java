package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

public class Piece {
    private final PieceColor pieceType;

    public Piece(PieceColor pieceType) {
        this.pieceType = pieceType;
    }

    public PieceColor getPieceType() {
        return pieceType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

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
