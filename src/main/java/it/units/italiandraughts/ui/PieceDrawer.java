package it.units.italiandraughts.ui;


import it.units.italiandraughts.logic.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class PieceDrawer {

    public void drawPieceOnSquare(Square square, Piece piece) {
        switch (piece.getPieceType()){
            case MAN -> drawManOnEmptySquare(piece, square);
            case KING -> drawKingOnEmptySquare(piece, square);
        }
    }

    private void drawManOnEmptySquare(Piece piece, Square square){
        double squareSize = square.getSquareSize();
        Ellipse baseEllipse = createEllipse(squareSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(squareSize * 0.07);

        Ellipse upperEllipse = createEllipse(squareSize);
        upperEllipse.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));

        square.getChildren().addAll(baseEllipse, upperEllipse);
    }

    public void drawKingOnEmptySquare(Piece piece, Square square) {
        double squareSize = square.getSquareSize();
        Ellipse baseEllipse = createEllipse(squareSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(squareSize * 0.06);

        Ellipse upperEllipse = createEllipse(squareSize);
        upperEllipse.setTranslateY(squareSize * -0.06);
        Ellipse middleEllipse = createEllipse(squareSize);
        middleEllipse.setFill(Color.valueOf("#c6c6c6"));
        Ellipse upperEllipse2 = createEllipse(squareSize);
        upperEllipse2.setTranslateY(squareSize * -0.1);
        upperEllipse2.setFill(Color.valueOf(piece.getPieceColor().getHexColor()));

        square.getChildren().addAll(baseEllipse, middleEllipse, upperEllipse, upperEllipse2);
    }

    private Ellipse createEllipse(double squareSize) {
        Ellipse ellipse = new Ellipse(squareSize * 0.3125, squareSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(squareSize * 0.03);
        return ellipse;
    }
}
