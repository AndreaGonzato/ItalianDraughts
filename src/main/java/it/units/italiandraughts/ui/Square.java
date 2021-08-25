package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Tile;
import javafx.event.Event;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Square extends StackPane {

    private final Tile tile;
    private boolean highlighted;
    private final TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";
    private final PropertyChangeSupport support;

    public Square(Tile tile, TileType type) {
        this.tile = tile;
        support = new PropertyChangeSupport(this);
        this.type = type;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
        this.setOnMouseClicked(this::notifyClick);
    }

    public Tile getTile() {
        return tile;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    private void notifyClick(Event event) {
        support.firePropertyChange("highlighted", isHighlighted(), !isHighlighted());
        highlight(!isHighlighted());
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    void highlight(boolean value) {
        if (tile.isEmpty()) {
            return;
        }
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.highlighted = value;
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

}
