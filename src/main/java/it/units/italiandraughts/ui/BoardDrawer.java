package it.units.italiandraughts.ui;

import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.GameEventListener;
import it.units.italiandraughts.event.SwitchActivePlayerEvent;
import it.units.italiandraughts.logic.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Optional;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class BoardDrawer implements GameEventListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final Game game;
    private Status status;
    private final PieceDrawer pieceDrawer;

    public BoardDrawer(GridPane gridPane, Game game) {
        this.game = game;
        pieceDrawer = new PieceDrawer();
        game.setDrawer(this);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        matrixToStream(game.getBoard().getTiles()).forEach(tile -> {
            Square square = new Square(tile,
                    ((tile.getX() + tile.getY()) % 2 == 0) ?
                            SquareType.BRONZE : SquareType.WHITE_SMOKE, gridPane.getMaxHeight() / Board.SIZE);
            squares[tile.getY()][tile.getX()] = square;
            gridPane.add(square, tile.getX(), tile.getY());
        });

        setClickableForPlayer(game.getPlayer1());
        setClickableForEmptySquares();
        status = Status.IDLE;

        // draw the pieces at the start
        updateBoard(game.getBoard().getTiles());
    }

    @Override
    public void onGameEvent(GameEvent event) {
        SwitchActivePlayerEvent switchActivePlayerEvent = (SwitchActivePlayerEvent) event;
        Player activePlayer = switchActivePlayerEvent.getPayload()[0];
        Player otherPlayer = switchActivePlayerEvent.getPayload()[1];
        setClickableForPlayer(activePlayer);
        unsetClickableForPlayer(otherPlayer);
        setClickableForEmptySquares();
    }

    public void clearHighlightingAndCircles() {
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
                .forEach(path -> path.getEndVertex().getSquare().placeGreenCircle());
    }

    private void onClickOnEmptySquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (Status.MOVE_IN_PROGRESS.equals(status)) {
            Optional<GraphPath<BlackTile, Edge>> absoluteLongestPathEndingOnClickedSquare = game.getAbsoluteLongestPaths().stream()
                    .filter(path -> path.getEndVertex().equals(square.getTile())
                            && path.getStartVertex().equals(game.getActiveTile()))
                    .findAny();
            if (absoluteLongestPathEndingOnClickedSquare.isPresent()) {
                List<BlackTile> steps = absoluteLongestPathEndingOnClickedSquare.get().getVertexList();
                game.makeMove(game.getActiveTile().getPiece(), steps);
            }
        }
    }

    private void setClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !(square.getTile().isEmpty()) &&
                        BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor()
                                .equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(this::onClickOnFullSquare));
    }

    private void unsetClickableForPlayer(Player player) {
        matrixToStream(squares).filter(square -> !(square.getTile().isEmpty()) &&
                        BlackTile.asBlackTile(square.getTile()).getPiece().getPieceColor()
                                .equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(null));
    }

    private void setClickableForEmptySquares() {
        matrixToStream(squares).filter(square -> square.getTile().isEmpty())
                .filter(square -> square.getType().equals(SquareType.BRONZE))
                .forEach(square -> square.setOnMouseClicked(this::onClickOnEmptySquare));
    }

    public void updateBoard(Tile[][] board) {
        matrixToStream(squares).forEach(tile -> tile.getChildren().clear());
        matrixToStream(board).filter(tile -> !tile.isEmpty())
                .forEach(tile -> pieceDrawer.drawPieceOnSquare(BlackTile.asBlackTile(tile).getPiece(),
                        squares[tile.getY()][tile.getX()]));
    }


}
