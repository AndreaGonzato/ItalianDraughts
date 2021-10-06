package it.units.italiandraughts.ui;


import it.units.italiandraughts.logic.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.List;

public class PieceDrawer {

    public void drawPieceOnSquare(Piece piece, Square square) {
        List<Ellipse> ellipses;
        double translateFactor = piece.isMan() ? 0.07 : 0.06;
        double squareSize = square.getSquareSize();
        Ellipse baseEllipse = createEllipse(squareSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(squareSize * translateFactor);

        if (piece.isMan()) {
            Ellipse upperEllipse = createEllipse(squareSize);
            upperEllipse.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));
            ellipses = List.of(baseEllipse, upperEllipse);
        } else {
            Ellipse upperEllipse = createEllipse(squareSize);
            upperEllipse.setTranslateY(squareSize * -1 * translateFactor);
            Ellipse middleEllipse = createEllipse(squareSize);
            middleEllipse.setFill(Color.valueOf("#c6c6c6"));
            Ellipse upperEllipse2 = createEllipse(squareSize);
            upperEllipse2.setTranslateY(squareSize * -0.1);
            upperEllipse2.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));
            ellipses = List.of(baseEllipse, middleEllipse, upperEllipse, upperEllipse2);
        }
        square.getChildren().addAll(ellipses);
    }

    private Ellipse createEllipse(double squareSize) {
        Ellipse ellipse = new Ellipse(squareSize * 0.3125, squareSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(squareSize * 0.03);
        return ellipse;
    }
}
