package it.units.italiandraughts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.units.italiandraughts.ui.MenuController;

import java.io.IOException;

public class ItalianDraughts extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/menuLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), MenuController.MIN_MENU_WIDTH, MenuController.MIN_MENU_HEIGHT);
        stage.setTitle("ItalianDraughts");
        stage.setScene(scene);
        primaryStage = stage;
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(MenuController.MIN_MENU_WIDTH);
        stage.setMinHeight(MenuController.MIN_MENU_HEIGHT);
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    // TODO remove this fake method
    public static int addOne(int n) {
        return n + 1;
    }
}