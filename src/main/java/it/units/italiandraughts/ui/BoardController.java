package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Game;
import it.units.italiandraughts.logic.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.List;

public class BoardController {

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

    private static double getBoardHeight() {
        return ItalianDraughts.getScreenHeight() / 3 * 2;
    }

    public void initialize() {
        gridPane.setMinSize(getBoardHeight(), getBoardHeight());
        gridPane.setMaxSize(getBoardHeight(), getBoardHeight());

        Board board = new Board();
        Player player1 = new Player(player1NameLabel.getText(), PieceType.PLAYER1);
        Player player2 = new Player(player2NameLabel.getText(), PieceType.PLAYER2);
        Game game = new Game(board, player1, player2);
        Drawer drawer = new Drawer(gridPane, game);
        game.addPropertyChangeListener(drawer);
        board.addPropertyChangeListener(drawer);
        board.notifyChange();
        game.notifyActivePlayerChange(null, player1);

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
    }

}
