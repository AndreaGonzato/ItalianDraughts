package it.units.italiandraughts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    @FXML
    protected void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("boardLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        ItalianDraughts.getPrimaryStage().setScene(scene);
    }

    @FXML
    protected void quitGame() {
        Platform.exit();
        System.exit(0);
    }

}