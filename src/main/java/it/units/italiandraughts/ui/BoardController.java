package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Piece;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class BoardController {
    @FXML
    GridPane board;

    @FXML
    Label player1Name, player2Name;

    private final double TILE_SIZE = 550/8;

    public void initialize() {
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();
                square.setPrefSize(800/5, 800/5);
                square.setMaxSize(800/5, 800/5);
                String color;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }

                // TODO just a test, try to add a single piece
                if (col == 4 && row == 2){
                    Piece piece = new Piece();
                    square.getChildren().addAll(piece.getEllipse1());
                    square.getChildren().add(piece.getEllipse2());
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
