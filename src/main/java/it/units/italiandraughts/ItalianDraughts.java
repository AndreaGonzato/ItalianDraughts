package it.units.italiandraughts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ItalianDraughts extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ItalianDraughts.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        primaryStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    protected static Stage getStage() {
        return primaryStage;
    }

    public static int addOne(int n) {
        return n + 1;
    }
}