package it.units.italiandraughts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    @FXML
    protected void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ItalianDraughts.class.getResource("secondScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        ItalianDraughts.getPrimaryStage().setTitle("ItalianDraughts");
        ItalianDraughts.getPrimaryStage().setScene(scene);
        ItalianDraughts.getPrimaryStage().show();
    }

    @FXML
    protected void quitGame() {
        Platform.exit();
        System.exit(0);
    }

}