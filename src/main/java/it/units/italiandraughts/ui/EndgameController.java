package it.units.italiandraughts.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndgameController {

    @FXML
    Label winnerLabel;

    public void initialize() {
        winnerLabel.setText("Andrea wins!");
    }
}
