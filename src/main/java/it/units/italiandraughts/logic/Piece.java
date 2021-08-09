package it.units.italiandraughts.logic;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {


    private static final double TILE_SIZE = 500/8;
    private Ellipse baseEllipse;
    private Ellipse upperEllipse;


    public Piece() {
        baseEllipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        baseEllipse.setFill(Color.BLACK);

        baseEllipse.setStroke(Color.BLACK);
        baseEllipse.setStrokeWidth(TILE_SIZE * 0.03);

        //baseEllipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        baseEllipse.setTranslateY(TILE_SIZE * 0.07);
        //baseEllipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);



        upperEllipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        upperEllipse.setFill( Color.valueOf("#fff9f4"));

        upperEllipse.setStroke(Color.BLACK);
        upperEllipse.setStrokeWidth(TILE_SIZE * 0.03);

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