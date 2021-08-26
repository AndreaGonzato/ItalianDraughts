package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Tile;
import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private final Tile tile;
    private final SquareType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";

    public Square(Tile tile, SquareType type) {
        this.tile = tile;
        this.type = type;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
        tile.setSquare(this);
    }

    public Tile getTile() {
        return tile;
    }

    void highlight(boolean value) {
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

    public SquareType getType() {
        return type;
    }
}
