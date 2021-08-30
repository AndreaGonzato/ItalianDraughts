package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Tile;
import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private final Tile tile;
    private final SquareType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";
    private boolean highlighted;

    public Square(Tile tile, SquareType squareType) {
        this.tile = tile;
        this.type = squareType;
        this.setStyle("-fx-background-color: " + squareType.getHex() + ";");
        tile.setSquare(this);
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    void setHighlighted(boolean value) {
        highlighted = value;
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

    public SquareType getType() {
        return type;
    }
}
