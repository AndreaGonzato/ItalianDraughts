package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.LogicTile;
import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class Drawer implements PropertyChangeListener {

    private final Tile[][] tiles = new Tile[Board.SIZE][Board.SIZE];
    private final GridPane gridPane;

    public Drawer(GridPane gridPane, LogicTile[][] tiles) {
        this.gridPane = gridPane;

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Tile square;
                if ((row + col) % 2 == 0) {
                    square = new Tile(tiles[row][col], TileType.BRONZE);
                } else {
                    square = new Tile(tiles[row][col], TileType.WHITE_SMOKE);
                }
                square.addPropertyChangeListener(this);
                this.tiles[row][col] = square;
                gridPane.add(square, col, row);
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "board" -> {
                LogicTile[][] board = (LogicTile[][]) evt.getNewValue();
                drawBoard(board);
            }
            case "highlighted" -> Arrays.stream(tiles).flatMap(Arrays::stream).forEach(t -> t.highlight(false));
        }

    }

    private void drawBoard(LogicTile[][] board) {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (!board[row][col].isEmpty()) {
                    drawPiece(tiles[row][col], board[row][col].getPiece());
                }
            }
        }
    }

    private void drawPiece(Tile tile, Piece piece) {
        double tileSize = gridPane.getMaxHeight() / 8;
        Ellipse baseEllipse = createEllipse(tileSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(tileSize * 0.07);

        Ellipse upperEllipse = createEllipse(tileSize);
        upperEllipse.setFill(Color.valueOf(piece.getPieceType().getHexColor()));

        tile.getChildren().add(baseEllipse);
        tile.getChildren().add(upperEllipse);
    }

    private Ellipse createEllipse(double tileSize) {
        Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(tileSize * 0.03);
        return ellipse;
    }

}
