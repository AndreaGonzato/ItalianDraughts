package it.units.italiandraughts.ui.elements;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class GameLabel extends Label {

    public GameLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameLabel.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

}
