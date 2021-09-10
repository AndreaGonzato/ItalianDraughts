package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class GameTest {

    @Test
    void firstActivePlayerPieceColor(){
        Game game = new Game(new Board(), new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));

        Player actualActivePlayer = game.getActivePlayer();

        Assertions.assertThat(actualActivePlayer.getPieceColor()).isEqualTo(PieceColor.WHITE);
    }

}
