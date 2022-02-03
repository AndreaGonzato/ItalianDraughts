package it.units.italiandraughts.logic.piece;

public enum PieceType {
    MAN, KING;

    @Override
    public String toString() {
        if (this.equals(MAN)) {
            return "MAN";
        } else return "KING";
    }
}
