package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Tile;
import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private final Tile tile;
    private final SquareType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";
    private boolean isHighlighted;

    public Square(Tile tile, SquareType type) {
        this.tile = tile;
        this.type = type;
        this.isHighlighted = false;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
        tile.setSquare(this);
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    void setHighlight(boolean value) {
        isHighlighted = value;
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

    public SquareType getType() {
        return type;
    }
}
