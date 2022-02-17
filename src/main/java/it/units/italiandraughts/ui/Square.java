package it.units.italiandraughts.ui;

import it.units.italiandraughts.exception.IllegalDrawingPositionException;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.Tile;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.List;

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
        hasGreenCircle = false;
    }

    Tile getTile() {
        return tile;
    }

    boolean isHighlighted() {
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
            throw new IllegalDrawingPositionException("Cannot draw on white square");
        }
        greenCircle = new Circle(squareSize * 0.15);
        greenCircle.setFill(Color.rgb(131, 235, 159, 0.6));
        this.getChildren().add(greenCircle);
    }

    void removeGreenCircle() {
        this.hasGreenCircle = false;
        this.getChildren().remove(greenCircle);
    }

    void drawPiece(Piece piece) {
        List<Ellipse> ellipses;
        double translateFactor = piece.isMan() ? 0.07 : 0.06;
        double squareSize = this.squareSize;
        Ellipse baseEllipse = EllipseDrawer.createEllipse(squareSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(squareSize * translateFactor);

        if (piece.isMan()) {
            Ellipse upperEllipse = EllipseDrawer.createEllipse(squareSize);
            upperEllipse.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));
            ellipses = List.of(baseEllipse, upperEllipse);
        } else {
            Ellipse upperEllipse = EllipseDrawer.createEllipse(squareSize);
            upperEllipse.setTranslateY(squareSize * -1 * translateFactor);
            Ellipse middleEllipse = EllipseDrawer.createEllipse(squareSize);
            middleEllipse.setFill(Color.valueOf("#c6c6c6"));
            Ellipse upperEllipse2 = EllipseDrawer.createEllipse(squareSize);
            upperEllipse2.setTranslateY(squareSize * -0.1);
            upperEllipse2.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));
            ellipses = List.of(baseEllipse, middleEllipse, upperEllipse, upperEllipse2);
        }
        this.getChildren().addAll(ellipses);
    }

    public boolean hasGreenCircle() {
        return hasGreenCircle;
    }

    public SquareType getType() {
        return type;
    }

}
