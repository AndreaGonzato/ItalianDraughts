package it.units.italiandraughts.ui;

import it.units.italiandraughts.exception.IllegalPositionDrawingException;
import it.units.italiandraughts.logic.tile.Tile;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Square extends StackPane {

    private final Tile tile;
    private final SquareType type;
    private static final String HIGHLIGHT_COLOR = "#56db39";
    private final double squareSize;
    private boolean highlighted;
    private boolean hasGreenCircle;
    private Circle greenCircle;


    public Square(Tile tile, SquareType squareType, double squareSize) {
        this.squareSize = squareSize;
        this.tile = tile;
        this.type = squareType;
        this.setStyle("-fx-background-color: " + squareType.getHex() + ";");
        tile.setSquare(this);
        hasGreenCircle = false;
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

    void placeGreenCircle() {
        this.hasGreenCircle = true;
        if (this.getType().equals(SquareType.WHITE_SMOKE)) {
            throw new IllegalPositionDrawingException("Cannot draw on white square");
        }
        greenCircle = new Circle(squareSize * 0.15);
        greenCircle.setFill(Color.rgb(131, 235, 159, 0.6));
        this.getChildren().add(greenCircle);
    }

    void removeGreenCircle() {
        this.hasGreenCircle = false;
        this.getChildren().remove(greenCircle);
    }

    public boolean hasGreenCircle() {
        return hasGreenCircle;
    }

    public SquareType getType() {
        return type;
    }

    public double getSquareSize() {
        return squareSize;
    }
}
