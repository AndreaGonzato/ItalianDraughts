package it.units.italiandraughts.ui;

public enum SquareType {
    BRONZE("#d47d35"), WHITE_SMOKE("#fafafa");

    private final String hex;

    SquareType(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
