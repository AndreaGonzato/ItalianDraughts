package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Game;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import javafx.scene.layout.GridPane;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class DrawingHandler extends UIHandler {

    DrawingHandler(Game game, GridPane gridPane) {
        super(game);

        matrixToStream(Board.getBoard().getTiles()).forEach(tile -> {
            Square square = new Square(tile,
                    ((tile.getX() + tile.getY()) % 2 == 0) ? SquareType.BRONZE : SquareType.WHITE_SMOKE,
                    gridPane.getMaxHeight() / Board.SIZE);
            squares[tile.getY()][tile.getX()] = square;
            gridPane.add(square, tile.getX(), tile.getY());
        });
    }

    void onClickOnFullSquare(Square square) {
        setActiveTileAndHighlightSquare(square);
        markDestinationsForStartingSquare(square);
    }

    // disegno
    private void highlightSquare(Square square) {
        clearHighlightingAndCircles();
        square.setHighlighted(true);
    }

    // disegno
    private void clearHighlightingAndCircles() {
        matrixToStream(squares).filter(Square::isHighlighted).forEach(square -> square.setHighlighted(false));
        matrixToStream(squares).filter(Square::hasGreenCircle).forEach(Square::removeGreenCircle);
    }



    // disegno
    private void markDestinationsForStartingSquare(Square square) {
        game.getAbsoluteLongestPaths()
                .stream()
                .filter(path -> path.getStartVertex().equals(square.getTile()))
                .forEach(path -> {
                    int x = path.getEndVertex().getX();
                    int y = path.getEndVertex().getY();
                    squares[y][x].placeGreenCircle();
                });
    }

    // user interaction e disegno
    private void setActiveTileAndHighlightSquare(Square square) {
        Tile tile = square.getTile();
        game.setActiveTile(BlackTile.asBlackTile(tile));
        highlightSquare(square);
    }

}
