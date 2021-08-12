package it.units.italiandraughts.ui;

public enum TileType {
    BRONZE("#d47d35"), WHITE_SMOKE("#fafafa");

    String hex;

    TileType(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
