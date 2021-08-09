package it.units.italiandraughts.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class InGameLabel extends Label {

    public InGameLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InGameLabel.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

}
