package it.units.italiandraughts.ui;

import it.units.italiandraughts.exception.IllegalPositionDrawingException;
import it.units.italiandraughts.exception.IllegalSquareClickException;
import it.units.italiandraughts.logic.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import org.jgrapht.GraphPath;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Optional;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Drawer implements PropertyChangeListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final GridPane gridPane;
    private final Game game;
    private final PieceDrawer pieceDrawer;

    public Drawer(GridPane gridPane, Game game) {
        this.gridPane = gridPane;
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

        // draw the pieces at the start
        updateBoard(game.getBoard().getTiles());
    }

    public Drawer reset() {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        return new Drawer(gridPane, game);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if ("activePlayer".equals(event.getPropertyName())) {
            Player nowPlaying = (Player) event.getNewValue();
            Player otherPlayer = (Player) event.getOldValue();
            setClickableForPlayer(nowPlaying);
            unsetClickableForPlayer(otherPlayer);
            setClickableForEmptySquares();
            BoardController boardController = (BoardController) gridPane.getUserData();
            boardController.getUndoButton().setDisable(game.getMoves().size() <= 0);
            boardController.getPlayerLabel(nowPlaying).setStyle("-fx-font-weight: bold;");
            boardController.getPlayerLabel(otherPlayer).setStyle("-fx-font-weight: normal;");
        }
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
        game.setStatus(Status.MOVE_IN_PROGRESS);
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
        if (square.getType().equals(SquareType.WHITE_SMOKE)){
            throw new IllegalSquareClickException("Do not click on White Square");
        }
        if (Status.MOVE_IN_PROGRESS.equals(game.getStatus())) {
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
                .forEach(tile -> pieceDrawer.drawPieceOnSquare(squares[tile.getY()][tile.getX()],
                        BlackTile.asBlackTile(tile).getPiece()));
    }

    private void drawGreenCircleOnEmptySquare(Square square) {
        if (square.getType().equals(SquareType.WHITE_SMOKE)) {
            throw new IllegalPositionDrawingException("Cannot draw on white square");
        }
        double tileSize = gridPane.getMaxHeight() / 8;
        Circle circle = new Circle(tileSize * 0.15);
        circle.setFill(Color.rgb(131, 235, 159, 0.6));
        square.getChildren().add(circle);
    }

}
