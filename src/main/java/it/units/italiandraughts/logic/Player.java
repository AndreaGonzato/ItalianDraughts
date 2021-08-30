package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;

import java.util.Objects;

public class Player {

    private String name;
    private final PieceColor pieceType;

    public Player(String name, PieceColor pieceType){
        this.pieceType = pieceType;
        this.name = name;
    }

    public PieceColor getPieceType() {
        return pieceType;
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
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (!Objects.equals(name, player.name)) return false;
        return pieceType == player.pieceType;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pieceType.hashCode();
        return result;
    }
}
