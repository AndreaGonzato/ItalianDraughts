package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Piece;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class BoardController {

    @FXML
    GridPane board;

    @FXML
    VBox rowNumbers;

    @FXML
    Label player1Name, player2Name;

    public void initialize() {
        board.setMinSize(
                ItalianDraughts.getScreenHeight()/3*2,
                ItalianDraughts.getScreenHeight()/3*2
        );
        board.setMaxSize(
                ItalianDraughts.getScreenHeight()/3*2,
                ItalianDraughts.getScreenHeight()/3*2
        );

        Stage stage = ItalianDraughts.getPrimaryStage();
        stage.sizeToScene();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

        List<Node> rowLabels = rowNumbers.getChildren();
        rowLabels.forEach(e -> {
            ((Label) e).setMaxHeight((ItalianDraughts.getScreenHeight() / 3 * 2) / 8);
            ((Label) e).setMinHeight((ItalianDraughts.getScreenHeight()/3*2)/8);
        });

        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "#d47d35";
                } else {
                    color = "white";
                }

                // TODO just a test, try to add a single piece
                if (col == 4 && row == 2){
                    Piece piece = new Piece(board.getMaxHeight()/8);
                    square.getChildren().addAll(piece.getBaseEllipse());
                    square.getChildren().add(piece.getUpperEllipse());
                }



                square.setStyle("-fx-background-color: " + color + ";");
                board.add(square, col, row);
            }
        }


        for (int i = 0; i < size; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(12.5);
            board.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(12.5);
            board.getRowConstraints().add(rowConstraints);
        }


    }

}
