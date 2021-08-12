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
}
