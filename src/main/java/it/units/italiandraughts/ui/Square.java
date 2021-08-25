package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Tile;
import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private final Tile tile;
    private boolean highlighted;
    private final TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";

    public Square(Tile tile, TileType type) {
        this.tile = tile;
        this.type = type;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
    }

    public Tile getTile() {
        return tile;
    }


    public boolean isHighlighted() {
        return highlighted;
    }

    void highlight(boolean value) {
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.highlighted = value;
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

}
