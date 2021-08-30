package it.units.italiandraughts.logic;

public enum PieceType {
    MAN, KING;

    @Override
    public String toString() {
        if (this.equals(MAN)) {
            return "MAN";
        } else return "KING";
    }
}
