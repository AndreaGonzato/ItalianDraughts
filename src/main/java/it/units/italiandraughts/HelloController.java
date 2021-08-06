package it.units.italiandraughts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    protected void startGame() {
        Group group = new Group();
        Scene scene = new Scene(group, 320, 240);
        Label text = new Label("TEST TEXT");
        text.setLayoutX(20);
        group.getChildren().addAll(text);
        ItalianDraughts.getStage().setScene(scene);
    }

    @FXML
    protected void quitGame() {
        Platform.exit();
        System.exit(0);
    }
}