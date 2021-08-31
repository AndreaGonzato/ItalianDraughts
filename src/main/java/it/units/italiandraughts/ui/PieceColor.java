package it.units.italiandraughts.ui;

public enum PieceColor {
    WHITE("#eeebd9"), BLACK("#423c39");

    private final String hexColor;

    PieceColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    @Override
    public String toString() {
        if (this.equals(WHITE)) {
            return "PLAYER1";
        } else return "PLAYER2";
    }
}
