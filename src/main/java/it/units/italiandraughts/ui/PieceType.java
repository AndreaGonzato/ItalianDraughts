package it.units.italiandraughts.ui;

public enum PieceType {
    PLAYER1("#eeebd9"), PLAYER2("#423c39");

    private final String hexColor;

    PieceType(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    @Override
    public String toString() {
        if (this.equals(PLAYER1)) {
            return "PLAYER1";
        } else return "PLAYER2";
    }
}
