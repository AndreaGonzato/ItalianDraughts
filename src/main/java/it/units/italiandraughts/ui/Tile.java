package it.units.italiandraughts.ui;

import javafx.event.Event;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {

    BoardDrawer drawer;
    private final int x;
    private final int y;
    private boolean highlighted;
    private boolean empty;
    private final TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";

    public Tile(BoardDrawer drawer, TileType type, int x, int y) {
        this.drawer = drawer;
        this.x = x;
        this.y = y;
        this.type = type;
        this.empty = true;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
        this.setOnMouseClicked(this::notifyClick);
    }

    private void notifyClick(Event event) {
        drawer.markAsClicked(x, y);
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

    void highlight(boolean value) {
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.highlighted = value;
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

}
