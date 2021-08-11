package it.units.italiandraughts.ui.elements;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class CoordinateLabel extends Label {

    public CoordinateLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CoordinateLabel.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

}
