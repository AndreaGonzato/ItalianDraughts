package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class GameTest {

    //@Test
    void firstActivePlayerPieceColor(){
        Game game = new Game(new Board(), new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));

        Player activePlayer = game.getActivePlayer();

        Assertions.assertThat(activePlayer.getPieceColor()).isEqualTo(PieceColor.WHITE);
    }

}
