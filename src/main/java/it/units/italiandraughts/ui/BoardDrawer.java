package it.units.italiandraughts.ui;

import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.GameEventListener;
import it.units.italiandraughts.event.SwitchActivePlayerEvent;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.logic.*;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class BoardDrawer implements GameEventListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final Game game;
    private Status status;

    public BoardDrawer(GridPane gridPane, Game game) {
        this.game = game;

        matrixToStream(Board.getBoard().getTiles()).forEach(tile -> {
            Square square = new Square(tile,
                    ((tile.getX() + tile.getY()) % 2 == 0) ? SquareType.BRONZE : SquareType.WHITE_SMOKE,
                    gridPane.getMaxHeight() / Board.SIZE);
            squares[tile.getY()][tile.getX()] = square;
            gridPane.add(square, tile.getX(), tile.getY());
        });

        setClickableForPlayer(game.getActivePlayer());
        setClickableForEmptySquares();
        status = Status.IDLE;

        // draw the pieces at the start
        updateBoard();
    }



    // disegna
    private void updateBoard() {
        matrixToStream(squares).forEach(square -> square.getChildren().clear());
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                        .forEach(square -> square.drawPiece(BlackTile.asBlackTile(square.getTile()).getPiece()));
    }


}
