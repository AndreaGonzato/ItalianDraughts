package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceType;

public class Player {
    private String name;
    private int score;
    private final PieceType pieceType;

    Player(String name, PieceType pieceType){
        this.pieceType = pieceType;
        this.name = name;
        score = 0;
    }
    
}
