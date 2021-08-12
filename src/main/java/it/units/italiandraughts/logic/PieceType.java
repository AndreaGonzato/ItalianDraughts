package it.units.italiandraughts.logic;

public enum PieceType {
    PLAYER1("#eeebd9"), PLAYER2("#423c39");

    private final String hexColor;

    PieceType(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }
}
