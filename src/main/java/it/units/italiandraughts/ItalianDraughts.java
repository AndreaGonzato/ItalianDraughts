package it.units.italiandraughts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ItalianDraughts extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ItalianDraughts.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 240);
        stage.setTitle("ItalianDraughts");
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

    public static void setPrimaryStage(Stage primaryStage) {
        ItalianDraughts.primaryStage = primaryStage;
    }

    // TODO remove this fake method
    public static int addOne(int n) {
        return n + 1;
    }
}