package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import javafx.fxml.FXML;
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
    Label player1Name, player2Name;

    public void initialize() {

        BoardDisplayer boardDisplayer = new BoardDisplayer(gridPane);
        Board board = new Board(boardDisplayer);
        Stage stage = ItalianDraughts.getPrimaryStage();
        stage.sizeToScene();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

        // add numbers left to the board
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(e -> {
            ((Label) e).setMaxHeight((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
            ((Label) e).setMinHeight((ItalianDraughts.getScreenHeight()/3*2)/8);
        });




    }

}
