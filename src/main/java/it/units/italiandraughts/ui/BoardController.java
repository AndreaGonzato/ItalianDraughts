package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Game;
import it.units.italiandraughts.logic.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BoardController implements PropertyChangeListener {

    @FXML
    GridPane gridPane;

    @FXML
    VBox rowNumbers;

    @FXML
    HBox columnLetters;

    @FXML
    Label player1NameLabel, player2NameLabel;

    @FXML
    Line line;

    @FXML
    Button undo;

    @FXML
    Button reset;

    private static double getBoardHeight() {
        return ItalianDraughts.getScreenHeight() / 3 * 2;
    }

    Label getPlayerLabel(Player player) {
        if (player.getName().equals(player1NameLabel.getText())) {
            return player1NameLabel;
        } else {
            return player2NameLabel;
        }
    }

    public void initializeWindow() {
        gridPane.setMinSize(getBoardHeight(), getBoardHeight());
        gridPane.setMaxSize(getBoardHeight(), getBoardHeight());

        Board board = new Board();
        Player player1 = new Player(player1NameLabel.getText(), PieceColor.WHITE);
        Player player2 = new Player(player2NameLabel.getText(), PieceColor.BLACK);
        Game game = new Game(board, player1, player2);
        Drawer drawer = new Drawer(gridPane, game);
        game.addPropertyChangeListener(drawer);
        game.addPropertyChangeListener(this);

        // resize the numbers to the left of board
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(e -> {
            Label label = (Label) e;
            label.setMaxHeight(getBoardHeight() / Board.SIZE);
            label.setMinHeight(getBoardHeight() / Board.SIZE);
        });

        Platform.runLater(() -> columnLetters.setPadding(
                new Insets(0, 0, 0, rowNumbers.getWidth())
        ));

        // resize the letters under the board
        List<Node> columnLabels = columnLetters.getChildren();
        columnLabels.forEach(e -> {
            Label label = (Label) e;
            label.setMaxWidth(getBoardHeight() / Board.SIZE);
            label.setMinWidth(getBoardHeight() / Board.SIZE);
        });

        line.setEndX(gridPane.getMaxWidth());
        gridPane.setStyle("-fx-border-color: #d47d35");
        reset.setOnAction(event -> game.reset());
        undo.setOnAction(event -> game.undo());
        undo.setDisable(true);
        player1NameLabel.setStyle("-fx-font-weight: bold;");
    }

    public Button getUndoButton() {
        return undo;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("winner".equals(event.getPropertyName())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndgameLayout.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), ItalianDraughts.getScreenWidth() / 5f, ItalianDraughts.getScreenHeight() / 5f);
                EndgameController controller = fxmlLoader.getController();
                controller.setWinner((Player) event.getNewValue());
                controller.initializeWindow();
                Stage stage = new Stage();
                controller.newGameButton.setOnAction((ee) -> {
                    gridPane.getColumnConstraints().clear();
                    gridPane.getRowConstraints().clear();
                    gridPane.getChildren().clear();
                    initializeWindow();
                    stage.hide();
                });
                stage.setScene(scene);
                stage.setTitle("Game over");
                stage.getIcons().add(new Image(Objects.requireNonNull(ItalianDraughts.class.getResourceAsStream("ui/img/icon.png"))));
                ItalianDraughts.setupStage(stage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
