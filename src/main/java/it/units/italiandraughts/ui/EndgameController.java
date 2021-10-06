package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Player;
import it.units.italiandraughts.ui.elements.GameButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndgameController {

    @FXML
    Label winnerLabel;

    @FXML
    GameButton newGameButton;

    private Player winner;

    @FXML
    private void quitGame() {
        Platform.exit();
        System.exit(0);
    }

    void initializeWindow() {
        winnerLabel.setText(winner.getName() + " wins!");
    }

    void setWinner(Player winner) {
        this.winner = winner;
    }
}
