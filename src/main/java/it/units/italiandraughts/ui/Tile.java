package it.units.italiandraughts.ui;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {

    private boolean highlighted;
    private boolean empty;
    private final TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";

    public Tile(TileType type) {
        this.type = type;
        this.empty = true;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
    }

    boolean isHighlighted() {
        return highlighted;
    }

    boolean isEmpty() {
        return empty;
    }

    void setEmpty(boolean value) {
        this.empty = value;
    }

    void unhighlightAll() {
        ObservableList<Node> children = this.getParent().getChildrenUnmodifiable();
        for (Node child : children) {
            if (child instanceof Tile tile) {
                if (tile.isHighlighted()) {
                    tile.highlight();
                }
            }
        }
    }

    void highlight() {
        String newColor;
        if (highlighted) {
            newColor = this.type.getHex();
        } else {
            newColor = HIGHLIGHT_COLOR;
        }
        highlighted = !highlighted;
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

    void clickHandler(Event event) {
        if (this.isEmpty()) {
            return;
        }
        this.unhighlightAll();
        this.highlight();
    }
}
