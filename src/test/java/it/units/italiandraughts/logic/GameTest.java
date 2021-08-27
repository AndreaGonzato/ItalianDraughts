package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void firstActivePlayerPieceType(){
        Game game = new Game(new Board(), new Player("", PieceType.PLAYER1), new Player("", PieceType.PLAYER2));

        Player actualActivePlayer = game.getActivePlayer();

        Assertions.assertEquals(PieceType.PLAYER1, actualActivePlayer.getPieceType());
    }

    @Test
    void move(){
        Game game = new Game(new Board(), new Player("", PieceType.PLAYER1), new Player("", PieceType.PLAYER2));
    }



}
