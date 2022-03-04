package it.units.italiandraughts.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class EllipseDrawer {

    static Ellipse createEllipse(double squareSize) {
        Ellipse ellipse = new Ellipse(squareSize * 0.3125, squareSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(squareSize * 0.03);
        return ellipse;
    }

}
