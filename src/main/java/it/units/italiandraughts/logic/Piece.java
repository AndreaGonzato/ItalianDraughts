package it.units.italiandraughts.logic;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {


    private static final double TILE_SIZE = 500/8;
    private Ellipse ellipse1;
    private Ellipse ellipse2;


    public Piece() {
        ellipse1 = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse1.setFill(Color.BLACK);

        ellipse1.setStroke(Color.BLACK);
        ellipse1.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse1.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse1.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);



        ellipse2 = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse2.setFill( Color.valueOf("#fff9f4"));

        ellipse2.setStroke(Color.BLACK);
        ellipse2.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse2.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse2.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

    }

    public Ellipse getEllipse1() {
        return ellipse1;
    }

    public Ellipse getEllipse2() {
        return ellipse2;
    }
}