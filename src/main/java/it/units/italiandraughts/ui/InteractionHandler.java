package it.units.italiandraughts.ui;

import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.SwitchActivePlayerEvent;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.logic.Game;
import it.units.italiandraughts.logic.Player;
import it.units.italiandraughts.logic.Status;
import it.units.italiandraughts.logic.tile.BlackTile;
import javafx.scene.input.MouseEvent;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class InteractionHandler extends UIHandler {

    private Status status;

    InteractionHandler(Game game) {
        super(game);
        setClickableForPlayer(game.getActivePlayer());
        setClickableForEmptySquares();
        status = Status.IDLE;
    }

    // user interaction
    private void onClickOnEmptySquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (Status.MOVE_IN_PROGRESS.equals(status)) {
            try {
                game.moveActivePieceTo(BlackTile.asBlackTile(square.getTile()));
            } catch (IllegalMoveException e) {
                return;
            }
            SoundPlayer.getSoundPlayer().playSound();
            status = Status.IDLE;
        }
    }

    // user interaction
    @Override
    public void onGameEvent(GameEvent event) {
        if (event instanceof SwitchActivePlayerEvent) {
            updateBoard();
            Player activePlayer = game.getActivePlayer();
            Player inactivePlayer = game.getInactivePlayer();
            setClickableForPlayer(activePlayer);
            unsetClickableForPlayer(inactivePlayer);
            setClickableForEmptySquares();
        }
    }

    // user interaction e disegno
    private void onClickOnFullSquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        status = Status.MOVE_IN_PROGRESS;
    }

    // user interaction
    private void setClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                .filter(square -> BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor().equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(this::handleClickOnSquare));
    }

    // user interaction
    private void unsetClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                .filter(square -> BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor().equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(null));
    }

    // user interaction
    private void setClickableForEmptySquares() {
        matrixToStream(squares).filter(square -> square.getType().equals(SquareType.BRONZE))
                .filter(square -> square.getTile().isEmpty())
                .forEach(square -> square.setOnMouseClicked(this::handleClickOnSquare));
    }

}
