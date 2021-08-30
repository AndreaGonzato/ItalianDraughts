package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void firstActivePlayerPieceType(){
        Game game = new Game(new Board(), new Player("", PieceColor.PLAYER1), new Player("", PieceColor.PLAYER2));

        Player actualActivePlayer = game.getActivePlayer();

        Assertions.assertEquals(PieceColor.PLAYER1, actualActivePlayer.getPieceType());
    }

    @Test
    void move(){
        Game game = new Game(new Board(), new Player("", PieceColor.PLAYER1), new Player("", PieceColor.PLAYER2));
    }



}