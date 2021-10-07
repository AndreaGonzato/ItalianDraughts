package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Game;
import it.units.italiandraughts.logic.Player;
import it.units.italiandraughts.logic.StaticUtil;
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

    private Game game;

    private static double getBoardHeight() {
        return StaticUtil.getScreenHeight() / 3 * 2;
    }

    private void showActivePlayerInBold(Player active) {
        if (active.getName().equals(player1NameLabel.getText())) {
            player1NameLabel.setStyle("-fx-font-weight: bold;");
            player2NameLabel.setStyle("-fx-font-weight: normal;");
        } else {
            player1NameLabel.setStyle("-fx-font-weight: normal;");
            player2NameLabel.setStyle("-fx-font-weight: bold;");
        }
    }

    public void initializeWindow() {
        gridPane.setMinSize(getBoardHeight(), getBoardHeight());
        gridPane.setMaxSize(getBoardHeight(), getBoardHeight());

        Board board = new Board();
        Player player1 = new Player(player1NameLabel.getText(), PieceColor.WHITE);
        Player player2 = new Player(player2NameLabel.getText(), PieceColor.BLACK);
        game = new Game(board, player1, player2);
        Drawer drawer = new Drawer(gridPane, game);
        game.addPropertyChangeListener(drawer);
        game.addPropertyChangeListener(this);

        // resize the numbers to the left of board
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(node -> {
            Label label = (Label) node;
            label.setMaxHeight(getBoardHeight() / Board.SIZE);
            label.setMinHeight(getBoardHeight() / Board.SIZE);
        });

        Platform.runLater(() -> columnLetters.setPadding(
                new Insets(0, 0, 0, rowNumbers.getWidth())
        ));

        // resize the letters under the board
        List<Node> columnLabels = columnLetters.getChildren();
        columnLabels.forEach(node -> {
            Label label = (Label) node;
            label.setMaxWidth(getBoardHeight() / Board.SIZE);
            label.setMinWidth(getBoardHeight() / Board.SIZE);
        });

        line.setEndX(gridPane.getMaxWidth());
        gridPane.setStyle("-fx-border-color: #d47d35");
        reset.setOnAction(event -> resetWindow());
        undo.setOnAction(event -> game.undo());
        undo.setDisable(true);
        showActivePlayerInBold(player1);
    }

    private void resetWindow() {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        initializeWindow();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("winner".equals(event.getPropertyName())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndgameLayout.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), StaticUtil.getScreenWidth() / 5f, StaticUtil.getScreenHeight() / 5f);
                EndgameController controller = fxmlLoader.getController();
                controller.setWinner((Player) event.getNewValue());
                controller.initializeWindow();
                Stage stage = new Stage();
                controller.newGameButton.setOnAction((clickEvent) -> {
                    resetWindow();
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
        } else if ("activePlayer".equals(event.getPropertyName())) {
            showActivePlayerInBold((Player) event.getNewValue());
            undo.setDisable(game.getMoves().size() <= 0);
        }
    }
}
