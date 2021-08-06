package it.units.italiandraughts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ItalianDraughts.class.getResource("secondScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        ItalianDraughts.getStage().setTitle("ItalianDraughts");
        ItalianDraughts.getStage().setScene(scene);
        ItalianDraughts.getStage().show();
    }

    @FXML
    protected void quitGame() {
        Platform.exit();
        System.exit(0);
    }

}