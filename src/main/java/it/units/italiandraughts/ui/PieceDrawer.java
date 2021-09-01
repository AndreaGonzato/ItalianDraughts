package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.BlackTile;
import it.units.italiandraughts.logic.Piece;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class PieceDrawer {

    private final GridPane gridPane;

    public PieceDrawer(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void drawPieceOnSquare(Square square, Piece piece) {
        double tileSize = gridPane.getMaxHeight() / 8;
        Ellipse baseEllipse = createEllipse(tileSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(tileSize * 0.07);

        Ellipse upperEllipse = createEllipse(tileSize);
        upperEllipse.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));

        square.getChildren().addAll(baseEllipse, upperEllipse);
    }

    public void drawKingOnEmptySquare(Square square) {
        double tileSize = gridPane.getMaxHeight() / 8;
        Ellipse baseEllipse = createEllipse(tileSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(tileSize * 0.06);
        Piece piece = new Piece(PieceColor.WHITE, BlackTile.asBlackTile(square.getTile()));

        Ellipse upperEllipse = createEllipse(tileSize);
        upperEllipse.setTranslateY(tileSize * -0.06);
        Ellipse middleEllipse = createEllipse(tileSize);
        middleEllipse.setFill(Color.valueOf("#c6c6c6"));
        Ellipse upperEllipse2 = createEllipse(tileSize);
        upperEllipse2.setTranslateY(tileSize * -0.1);
        upperEllipse2.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));

        square.getChildren().addAll(baseEllipse, middleEllipse, upperEllipse, upperEllipse2);
    }

    private Ellipse createEllipse(double tileSize) {
        Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(tileSize * 0.03);
        return ellipse;
    }
}
