package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class BoardDisplayer {
    private final int SIZE = 8;
    private GridPane gridPane;
    private StackPane[][] tiles;
    private double tileSize;

    public BoardDisplayer(GridPane gridPane) {
        this.gridPane = gridPane;
        tiles = new StackPane[SIZE][SIZE];

        gridPane.setMinSize(
                ItalianDraughts.getScreenHeight() / 3 * 2,
                ItalianDraughts.getScreenHeight() / 3 * 2
        );
        gridPane.setMaxSize(
                ItalianDraughts.getScreenHeight() / 3 * 2,
                ItalianDraughts.getScreenHeight() / 3 * 2
        );

        tileSize = gridPane.getMaxHeight() / 8;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                StackPane square = new StackPane();
                tiles[row][col] = square;
                String color;
                if ((row + col) % 2 == 0) {
                    color = "#d47d35";
                } else {
                    color = "white";
                }

                square.setStyle("-fx-background-color: " + color + ";");
                gridPane.add(square, col, row);
            }
        }


        for (int i = 0; i < SIZE; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(12.5);
            gridPane.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(12.5);
            gridPane.getRowConstraints().add(rowConstraints);
        }


    }

    public void draw(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0) {
                    Piece piece = new Piece(tileSize);
                    tiles[i][j].getChildren().add(piece.getBaseEllipse());
                    tiles[i][j].getChildren().add(piece.getUpperEllipse());
                }
            }
        }
    }
}
