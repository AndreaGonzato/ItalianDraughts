package it.units.italiandraughts.ui;

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

        //baseEllipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        baseEllipse.setTranslateY(tileSize * 0.07);
        //baseEllipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);


        upperEllipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        if (pieceType == PieceType.PLAYER1) {
            upperEllipse.setFill(Color.valueOf("#fff9f4"));
        }else {
            upperEllipse.setFill(Color.valueOf("#423c39"));
        }

        upperEllipse.setStroke(Color.BLACK);
        upperEllipse.setStrokeWidth(tileSize * 0.03);

        //upperEllipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        //upperEllipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

    }

    public Ellipse getBaseEllipse() {
        return baseEllipse;
    }

    public Ellipse getUpperEllipse() {
        return upperEllipse;
    }
}