package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.PieceType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {


    private Ellipse baseEllipse;
    private Ellipse upperEllipse;
    private PieceType pieceType;


    public Piece(PieceType pieceType, double tileSize) {
        this.pieceType = pieceType;
        baseEllipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setStroke(Color.BLACK);
        baseEllipse.setStrokeWidth(tileSize * 0.03);
        baseEllipse.setTranslateY(tileSize * 0.07);


        upperEllipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        if (pieceType == PieceType.PLAYER1) {
            upperEllipse.setFill(Color.valueOf("#e8e8e8")); // White
        } else {
            upperEllipse.setFill(Color.valueOf("#423c39"));
        }
        upperEllipse.setStroke(Color.BLACK);
        upperEllipse.setStrokeWidth(tileSize * 0.03);

    }

    public Ellipse getBaseEllipse() {
        return baseEllipse;
    }

    public Ellipse getUpperEllipse() {
        return upperEllipse;
    }
}