package it.units.italiandraughts.ui;

import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.exception.IllegalPositionDrawingException;
import it.units.italiandraughts.logic.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class Drawer implements PropertyChangeListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final GridPane gridPane;
    private final Game game;
    private final PieceDrawer pieceDrawer;

    public Drawer(GridPane gridPane, Game game) {
        this.gridPane = gridPane;
        this.game = game;
        pieceDrawer = new PieceDrawer(gridPane);
        game.setDrawer(this);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        Arrays.stream(game.getBoard().getTiles()).flatMap(Arrays::stream)
                .forEach(tile -> {
                    Square square = new Square(tile,
                            ((tile.getX() + tile.getY()) % 2 == 0) ?
                            SquareType.BRONZE : SquareType.WHITE_SMOKE);
                    squares[tile.getY()][tile.getX()] = square;
                    gridPane.add(square, tile.getX(), tile.getY());
                });

        setClickableForPlayer(game.getPlayer1());
        setClickableForEmptySquares();

        // draw the pieces at the start
        updateBoard(game.getBoard().getTiles());
    }


    public void propertyChange(PropertyChangeEvent event) {
        // TODO remove this if if no other PropertyChangeEvent are used
        if ("activePlayer".equals(event.getPropertyName())) {
            setClickableForPlayer((Player) event.getNewValue());
            unsetClickableForPlayer((Player) event.getOldValue());
            setClickableForEmptySquares();
            BoardController controller = (BoardController) gridPane.getUserData();
            controller.getUndoButton().setDisable(game.getLog().size() <= 0);
        }
    }

    public void turnOffHighlightedSquares(){
        Arrays.stream(squares).flatMap(Arrays::stream).filter(Square::isHighlighted).forEach(t -> t.setHighlighted(false));
    }

    private void highlightSquare(Square square) {
        turnOffHighlightedSquares();
        square.setHighlighted(true);
    }

    public void onClickOnFullSquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        setActiveTileAndHighlightSquare(square);
        game.setStatus(Status.MOVE_IN_PROGRESS);
    }

    public void setActiveTileAndHighlightSquare(Square square) {
        game.setActiveTile(square.getTile());
        highlightSquare(square);
    }

    public void onClickOnEmptySquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (Status.MOVE_IN_PROGRESS.equals(game.getStatus())) {
            try {
                game.move(game.getActiveTile().getX(),
                        game.getActiveTile().getY(),
                        square.getTile().getX(),
                        square.getTile().getY(),
                        true);
            } catch (IllegalMoveException e) {
                return;
            }
            updateBoard(game.getBoard().getTiles());
            game.setActiveTile(null);
            game.setStatus(Status.IDLE);
        }
    }

    public void setClickableForPlayer(Player player) {
        Arrays.stream(squares).flatMap(Arrays::stream)
                .filter(square -> !(square.getTile().isEmpty()) && square.getTile().getPiece().getPieceColor()
                        .equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(this::onClickOnFullSquare));
    }

    public void unsetClickableForPlayer(Player player) {
        Arrays.stream(squares).flatMap(Arrays::stream)
                .filter(square -> !(square.getTile().isEmpty()) && square.getTile().getPiece().getPieceColor()
                        .equals(player.getPieceColor()))
                .forEach(square -> square.setOnMouseClicked(null));
    }

    public void setClickableForEmptySquares() {
        Arrays.stream(squares).flatMap(Arrays::stream)
                .filter(square -> square.getTile().isEmpty())
                .forEach(square -> square.setOnMouseClicked(this::onClickOnEmptySquare));
    }

    public void updateBoard(Tile[][] board) {
        Arrays.stream(squares).flatMap(Arrays::stream).forEach(tile -> tile.getChildren().clear());
        Arrays.stream(board).flatMap(Arrays::stream).filter(tile -> !tile.isEmpty())
                .forEach(tile -> pieceDrawer.drawPieceOnSquare(squares[tile.getY()][tile.getX()], tile.getPiece()));
        drawGreenCircleOnEmptySquare(squares[4][2]); // TODO test draw a single greenCircle, remove this line
        pieceDrawer.drawKingOnEmptySquare(squares[4][4]); // TODO remove this
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
