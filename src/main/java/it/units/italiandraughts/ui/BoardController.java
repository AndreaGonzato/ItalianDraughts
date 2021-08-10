package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class BoardController {

    @FXML
    GridPane gridPane;

    @FXML
    VBox rowNumbers;

    @FXML
    HBox columnLetters;

    @FXML
    Label player1Name, player2Name;

    public void initialize() {
        BoardDisplayer boardDisplayer = new BoardDisplayer(gridPane);
        Board board = new Board(boardDisplayer);
        board.draw(boardDisplayer);
        Stage stage = ItalianDraughts.getPrimaryStage();
        stage.sizeToScene();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

        // resize the numbers to the left of board
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(e -> {
            ((Label) e).setMaxHeight((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
            ((Label) e).setMinHeight((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
        });

        Platform.runLater(() -> columnLetters.setPadding(new Insets(0, 0, 0,
                rowNumbers.getWidth())));

        // resize the letters under the board
        List<Node> columnLabels = columnLetters.getChildren();
        columnLabels.forEach(e -> {
            ((Label) e).setMaxWidth((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
            ((Label) e).setMinWidth((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
            ((Label) e).setAlignment(Pos.CENTER);
        });
    }

}
