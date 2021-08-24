package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.LogicTile;
import javafx.event.Event;
import javafx.scene.layout.StackPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Tile extends StackPane {

    private final int x;
    private final int y;
    private LogicTile logicTile;
    private boolean highlighted;
    private final TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";
    private final PropertyChangeSupport support;

    public Tile(TileType type, int x, int y) {
        support = new PropertyChangeSupport(this);
        this.x = x;
        this.y = y;
        this.type = type;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
        this.setOnMouseClicked(this::notifyClick);
    }


    public void bindToLogicTile(LogicTile logicTile) {
        this.logicTile = logicTile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LogicTile getLogicTile() {
        return logicTile;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }


    private void notifyClick(Event event) {
        support.firePropertyChange("highlighted", isHighlighted(), !isHighlighted());
        highlight(!isHighlighted());
    }

    boolean isHighlighted() {
        return highlighted;
    }

    void highlight(boolean value) {
        if (logicTile.isEmpty()) {
            return;
        }
        String newColor = value ? HIGHLIGHT_COLOR : this.type.getHex();
        this.highlighted = value;
        this.setStyle("-fx-background-color: " + newColor + ";");
    }

}
