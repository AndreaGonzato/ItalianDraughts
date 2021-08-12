package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class PieceDrawer extends StackPane {


    private final Ellipse baseEllipse;
    private final Ellipse upperEllipse;
    private final Piece piece;


    public PieceDrawer(Piece piece, double tileSize) {
        this.piece = piece;

        baseEllipse = createEllipse(tileSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(tileSize * 0.07);

        upperEllipse = createEllipse(tileSize);
        upperEllipse.setFill(Color.valueOf(piece.getPieceType().getHexColor()));
    }

    private Ellipse createEllipse(double tileSize) {
        Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(tileSize * 0.03);
        return ellipse;
    }

    public Piece getPiece() {
        return piece;
    }

    public void draw(StackPane tile){
        tile.getChildren().add(baseEllipse);
        tile.getChildren().add(upperEllipse);

    }

    public Ellipse getBaseEllipse() {
        return baseEllipse;
    }

    public Ellipse getUpperEllipse() {
        return upperEllipse;
    }
}