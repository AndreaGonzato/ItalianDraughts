package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceType;

public class Player {
    private String name;
    private int score;
    private final PieceType pieceType;

    public Player(String name, PieceType pieceType){
        this.pieceType = pieceType;
        this.name = name;
        score = 0;
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

        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        return pieceType == player.pieceType;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pieceType.hashCode();
        return result;
    }
}
