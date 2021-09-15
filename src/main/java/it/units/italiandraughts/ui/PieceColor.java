package it.units.italiandraughts.ui;

public enum PieceColor {
    WHITE("#eeebd9", 0), BLACK("#423c39", 7);

    private final String hexColor;
    private final int promotionRow;

    PieceColor(String hexColor, int promotionRow) {
        this.hexColor = hexColor;
        this.promotionRow = promotionRow;
    }

    public int getPromotionRow() {
        return promotionRow;
    }

    public String getHexColor() {
        return hexColor;
    }

    @Override
    public String toString() {
        if (this.equals(WHITE)) {
            return "WHITE";
        } else return "BLACK";
    }
}
