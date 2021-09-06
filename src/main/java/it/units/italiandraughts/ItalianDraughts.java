package it.units.italiandraughts;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ItalianDraughts extends Application {

    private static final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private static final double screenHeight = Screen.getPrimary().getBounds().getHeight();

    public static <T> Stream<T> matrixToStream(T[][] matrix) {
        return Arrays.stream(matrix).flatMap(Arrays::stream);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/MenuLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWidth / 4f, screenHeight / 4f);
        stage.setTitle("ItalianDraughts");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("ui/img/icon.png"))));
        stage.setScene(scene);
        setupStage(stage);
        stage.show();
    }

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

    public static double getScreenWidth() {
        return screenWidth;
    }

    public static double getScreenHeight() {
        return screenHeight;
    }
    
}