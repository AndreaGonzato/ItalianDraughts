package it.units.italiandraughts.ui;

public enum Color {
    BRONZE("#d47d35"), WHITE_SMOKE("#fafafa");

    String hex;

    Color(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
