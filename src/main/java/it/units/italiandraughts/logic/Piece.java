package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

public class Piece {
    private final PieceColor pieceColor;
    private PieceType pieceType;

    public Piece(PieceColor pieceColor) {
        this(pieceColor, PieceType.MAN);
    }

    public Piece(PieceColor pieceColor, PieceType pieceType) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;

        return pieceColor == piece.pieceColor && pieceType == piece.pieceType;
    }

    @Override
    public String toString() {
        return pieceColor + ", " + pieceType;
    }

    @Override
    public int hashCode() {
        return pieceColor.hashCode();
    }
}
