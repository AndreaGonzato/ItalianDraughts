package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

import java.util.Objects;

public class Player {

    private final String name;
    private final PieceColor pieceColor;

    public Player(String name, PieceColor pieceColor){
        this.pieceColor = pieceColor;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;

        if (!Objects.equals(name, player.name)) return false;
        return pieceColor == player.pieceColor;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pieceColor.hashCode();
        return result;
    }
}
