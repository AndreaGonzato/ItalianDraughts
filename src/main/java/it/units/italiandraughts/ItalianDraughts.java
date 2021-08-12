package it.units.italiandraughts;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ItalianDraughts extends Application {

    private static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/MenuLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWidth / 4, screenHeight / 4);
        stage.setTitle("ItalianDraughts");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("ui/img/icon.png")));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
    
}