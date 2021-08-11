package it.units.italiandraughts.ui.elements;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class GameButton extends Button {

    public GameButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameButton.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

}