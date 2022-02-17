package it.units.italiandraughts.ui;

import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.GameEventListener;
import it.units.italiandraughts.event.SwitchActivePlayerEvent;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.logic.*;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Objects;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class BoardDrawer implements GameEventListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final Game game;
    private Status status;
    private final MediaPlayer mediaPlayer;

    public BoardDrawer(GridPane gridPane, Game game) {
        this.game = game;

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        matrixToStream(Board.getBoard().getTiles()).forEach(tile -> {
            Square square = new Square(tile,
                    ((tile.getX() + tile.getY()) % 2 == 0) ? SquareType.BRONZE : SquareType.WHITE_SMOKE,
                    gridPane.getMaxHeight() / Board.SIZE);
            squares[tile.getY()][tile.getX()] = square;
            gridPane.add(square, tile.getX(), tile.getY());
        });

        mediaPlayer = initMediaPlayer();

        setClickableForPlayer(game.getPlayer1());
        setClickableForEmptySquares();
        status = Status.IDLE;

        // draw the pieces at the start
        updateBoard();
    }

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

    private void clearHighlightingAndCircles() {
        matrixToStream(squares).filter(Square::isHighlighted).forEach(square -> square.setHighlighted(false));
        matrixToStream(squares).filter(Square::hasGreenCircle).forEach(Square::removeGreenCircle);
    }

    private void highlightSquare(Square square) {
        clearHighlightingAndCircles();
        square.setHighlighted(true);
    }

    private void onClickOnFullSquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        setActiveTileAndHighlightSquare(square);
        status = Status.MOVE_IN_PROGRESS;
    }

    private void setActiveTileAndHighlightSquare(Square square) {
        Tile tile = square.getTile();
        game.setActiveTile(BlackTile.asBlackTile(tile));
        highlightSquare(square);

        game.getAbsoluteLongestPaths()
                .stream()
                .filter(path -> path.getStartVertex().equals(tile))
                .forEach(path -> {
                    int x = path.getEndVertex().getX();
                    int y = path.getEndVertex().getY();
                    squares[y][x].placeGreenCircle();
                });
    }

    private MediaPlayer initMediaPlayer() {
        String path = "sounds" + File.separatorChar + "movePiece.mp3";
        URL resource = Objects.requireNonNull(getClass().getResource(path));
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
    }

    private void playSound() {
        new Thread(() -> {
            mediaPlayer.play();
            mediaPlayer.seek(new Duration(0));
        }).start();
    }

    private void onClickOnEmptySquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (Status.MOVE_IN_PROGRESS.equals(status)) {
            try {
                game.moveActivePieceTo(BlackTile.asBlackTile(square.getTile()));
            } catch (IllegalMoveException e){
                return;
            }
            playSound();
        }
    }

    private void setClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                .filter(square -> BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor().equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(this::onClickOnFullSquare));
    }

    private void unsetClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                .filter(square -> BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor().equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(null));
    }

    private void setClickableForEmptySquares() {
        matrixToStream(squares).filter(square -> square.getType().equals(SquareType.BRONZE))
                .filter(square -> square.getTile().isEmpty())
                .forEach(square -> square.setOnMouseClicked(this::onClickOnEmptySquare));
    }

    private void updateBoard() {
        matrixToStream(squares).forEach(square -> square.getChildren().clear());
        matrixToStream(squares).filter(square -> !square.getTile().isEmpty())
                        .forEach(square -> square.drawPiece(BlackTile.asBlackTile(square.getTile()).getPiece()));
    }


}
