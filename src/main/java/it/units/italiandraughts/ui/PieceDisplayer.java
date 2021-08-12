package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class PieceDisplayer extends StackPane {


    private Ellipse baseEllipse;
    private Ellipse upperEllipse;
    private Piece piece;


    public PieceDisplayer(Piece piece, double tileSize) {
        this.piece = piece;
        baseEllipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setStroke(Color.BLACK);
        baseEllipse.setStrokeWidth(tileSize * 0.03);
        baseEllipse.setTranslateY(tileSize * 0.07);


        upperEllipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        if (piece.getPieceType().equals(PieceType.PLAYER1)) {
            upperEllipse.setFill(Color.valueOf(PieceType.PLAYER1.getHexColor())); // White
        } else {
            upperEllipse.setFill(Color.valueOf(PieceType.PLAYER2.getHexColor()));
        }
        upperEllipse.setStroke(Color.BLACK);
        upperEllipse.setStrokeWidth(tileSize * 0.03);

    }

    public Piece getPiece() {
        return piece;
    }

    public Ellipse getBaseEllipse() {
        return baseEllipse;
    }

    public Ellipse getUpperEllipse() {
        return upperEllipse;
    }
}