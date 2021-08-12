package it.units.italiandraughts.ui;

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
    Label player1Name, player2Name;

    @FXML
    Line line;

    @FXML
    Button undo;

    public void initialize() {
        BoardDrawer boardDrawer = new BoardDrawer(gridPane);
        Board board = new Board();
        board.draw(boardDrawer);
        Player player1 = new Player(player1Name.getText(), PieceType.PLAYER1);
        Player player2 = new Player(player2Name.getText(), PieceType.PLAYER2);
        Game game = new Game(board, player1, player2);

        board.move(0, 0, 4, 4);
        board.draw(boardDrawer);
        System.out.println(board);

        // resize the numbers to the left of board
        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(e -> {
            Label label = (Label) e;
            label.setMaxHeight(BoardDrawer.getBoardHeight() / Board.SIZE);
            label.setMinHeight(BoardDrawer.getBoardHeight() / Board.SIZE);
        });

        Platform.runLater(() -> columnLetters.setPadding(
                new Insets(0, 0, 0, rowNumbers.getWidth()
                )));

        // resize the letters under the board
        List<Node> columnLabels = columnLetters.getChildren();
        columnLabels.forEach(e -> {
            Label label = (Label) e;
            label.setMaxWidth(BoardDrawer.getBoardHeight() / Board.SIZE);
            label.setMinWidth(BoardDrawer.getBoardHeight() / Board.SIZE);
        });

        line.setEndX(gridPane.getMaxWidth());
        player1Name.setPadding(new Insets(0, 15, 0, 0));
        player2Name.setPadding(new Insets(0, 0, 0, 15));
        gridPane.setStyle("-fx-border-color: #d47d35");
    }

}
