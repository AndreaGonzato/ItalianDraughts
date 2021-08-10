package it.units.italiandraughts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import it.units.italiandraughts.ui.MenuController;

import java.io.IOException;

public class ItalianDraughts extends Application {

    private static Stage primaryStage;
    private static int screenWidth;
    private static int screenHeight;

    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/menuLayout.fxml"));
        screenWidth = (int) screenBounds.getWidth();
        screenHeight = (int) screenBounds.getHeight();
        primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), screenWidth / 4, screenHeight / 4);
        stage.setTitle("ItalianDraughts");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);
        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    // TODO remove this fake method
    public static int addOne(int n) {
        return n + 1;
    }
}