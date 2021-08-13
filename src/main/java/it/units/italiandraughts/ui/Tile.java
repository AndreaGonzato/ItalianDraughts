package it.units.italiandraughts.ui;

import javafx.scene.layout.StackPane;

public class Tile extends StackPane {

    boolean highlighted;
    TileType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";

    public Tile(TileType type) {
        this.type = type;
        this.setStyle("-fx-background-color: " + type.getHex() + ";");
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

}
