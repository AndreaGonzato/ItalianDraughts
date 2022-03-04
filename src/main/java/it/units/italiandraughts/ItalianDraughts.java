package it.units.italiandraughts;


import it.units.italiandraughts.logic.StaticUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ItalianDraughts extends Application {

    public static void setupStage(Stage stage) {
        stage.sizeToScene();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/MenuLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), StaticUtil.getScreenWidth() / 4f, StaticUtil.getScreenHeight() / 4f);
        stage.setTitle("ItalianDraughts");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("ui/img/icon.png"))));
        stage.setScene(scene);
        setupStage(stage);
        stage.show();
    }

}