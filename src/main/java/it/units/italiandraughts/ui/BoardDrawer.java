package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class BoardDrawer {
    private StackPane[][] tiles;
    private final double tileSize;

    protected static float getBoardHeight() {
        return ItalianDraughts.getScreenHeight() / 3f * 2;
    }

    public BoardDrawer(GridPane gridPane) {
        tiles = new StackPane[Board.SIZE][Board.SIZE];

        gridPane.setMinSize(getBoardHeight(), getBoardHeight());
        gridPane.setMaxSize(getBoardHeight(), getBoardHeight());

        tileSize = gridPane.getMaxHeight() / 8;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                StackPane square = new StackPane();
                tiles[row][col] = square;
                String color;
                if ((row + col) % 2 == 0) {
                    color = TileType.BRONZE.getHex();
                } else {
                    color = TileType.WHITE_SMOKE.getHex();
                }

                square.setStyle("-fx-background-color: " + color + ";");
                gridPane.add(square, col, row);
            }
        }

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }


    }

    public void draw(Piece[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Piece piece = matrix[i][j];
                if (piece != null) {
                    new PieceDrawer(piece, tileSize).draw(tiles[i][j]);
                }
            }
        }
    }

    public double getTileSize() {
        return tileSize;
    }


}
