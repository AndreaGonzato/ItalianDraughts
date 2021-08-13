package it.units.italiandraughts.ui;

import it.units.italiandraughts.ItalianDraughts;
import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BoardDrawer {
    private final Tile[][] tiles;
    private final double tileSize;

    protected static double getBoardHeight() {
        return ItalianDraughts.getScreenHeight() / 3 * 2;
    }

    public BoardDrawer(GridPane gridPane) {
        tiles = new Tile[Board.SIZE][Board.SIZE];

        gridPane.setMinSize(getBoardHeight(), getBoardHeight());
        gridPane.setMaxSize(getBoardHeight(), getBoardHeight());

        tileSize = gridPane.getMaxHeight() / 8;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Tile square;
                if ((row + col) % 2 == 0) {
                    square = new Tile(TileType.BRONZE);
                } else {
                    square = new Tile(TileType.WHITE_SMOKE);
                }
                tiles[row][col] = square;
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
                tiles[i][j].getChildren().clear();
                Piece piece = matrix[i][j];
                if (piece != null) {
                    new PieceDrawer(piece, tileSize).draw(tiles[i][j]);
                } else {
                    tiles[i][j].setEmpty(true);
                }
            }
        }
    }

    public double getTileSize() {
        return tileSize;
    }


}
