package it.units.italiandraughts.ui;

import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.exception.IllegalPositionDrawingException;
import it.units.italiandraughts.logic.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.input.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.function.Predicate;

public class Drawer implements PropertyChangeListener {

    private final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    private final GridPane gridPane;
    private final Game game;

    public Drawer(GridPane gridPane, Game game) {
        this.gridPane = gridPane;
        this.game = game;

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(12.5);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(12.5);
        for (int i = 0; i < Board.SIZE; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Square square;
                if ((row + col) % 2 == 0) {
                    square = new Square(game.getBoard().getTiles()[row][col], SquareType.BRONZE);
                } else {
                    square = new Square(game.getBoard().getTiles()[row][col], SquareType.WHITE_SMOKE);
                }
                this.squares[row][col] = square;
                game.getBoard().getTiles()[row][col].setSquare(square);
                gridPane.add(square, col, row);
            }
        }
        setOnMouseClickedBasedOnPredicate(
                s -> !(s.getTile().isEmpty()) && s.getTile().getPiece().getPieceType()
                        .equals(game.getPlayer1().getPieceType()));

        // draw the pieces at the start
        updateBoard(game.getBoard().getTiles());
    }

    public void propertyChange(PropertyChangeEvent event) {
        if ("activePlayer".equals(event.getPropertyName())) {
            unsetOnMouseClickedForAllSquares();
            setOnMouseClickedBasedOnPredicate(
                    s -> !(s.getTile().isEmpty()) && s.getTile().getPiece().getPieceType()
                            .equals(((Player) event.getNewValue()).getPieceType())
            );
        }
    }

    private void highlight(Square square) {
        Arrays.stream(squares).flatMap(Arrays::stream).forEach(t -> t.highlight(false));
        square.highlight(true);
    }

    public void onClickOnSquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        switch (game.getStatus()) {
            case IDLE -> {
                game.setSource(square.getTile());
                highlight(square);
                unsetOnMouseClickedForAllSquares();
                setOnMouseClickedBasedOnPredicate(s -> (s.getTile().isEmpty()));
                game.setStatus(Status.MOVE_IN_PROGRESS);
            }
            case MOVE_IN_PROGRESS -> {
                try {
                    game.move(game.getSource().getX(),
                            game.getSource().getY(),
                            square.getTile().getX(),
                            square.getTile().getY());
                } catch (IllegalMoveException e) {
                    return;
                }
                updateBoard(game.getBoard().getTiles());
                game.setSource(null);
                game.setStatus(Status.IDLE);
            }
        }
    }

    private void setOnMouseClickedBasedOnPredicate(Predicate<? super Square> predicate) {
        Arrays.stream(squares).flatMap(Arrays::stream)
                .filter(predicate)
                .forEach(s -> s.setOnMouseClicked(this::onClickOnSquare));
    }

    private void unsetOnMouseClickedForAllSquares() {
        Arrays.stream(squares).flatMap(Arrays::stream)
                .forEach(s -> s.setOnMouseClicked(null));
    }

    private void updateBoard(Tile[][] board) {
        Arrays.stream(squares).flatMap(Arrays::stream).forEach(t -> t.getChildren().clear());
        Arrays.stream(board).flatMap(Arrays::stream).filter(t -> !t.isEmpty())
                .forEach(t -> drawPiece(squares[t.getY()][t.getX()], t.getPiece()));
        drawGreenCircleOnEmptySquare(squares[4][2]); // TODO test draw a single greenCircle, remove this line
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

    private void drawPiece(Square square, Piece piece) {
        double tileSize = gridPane.getMaxHeight() / 8;
        Ellipse baseEllipse = createEllipse(tileSize);
        baseEllipse.setFill(Color.BLACK);
        baseEllipse.setTranslateY(tileSize * 0.07);

        Ellipse upperEllipse = createEllipse(tileSize);
        upperEllipse.setFill(Color.valueOf(piece.getPieceType().getHexColor()));

        square.getChildren().addAll(baseEllipse, upperEllipse);
    }

    private Ellipse createEllipse(double tileSize) {
        Ellipse ellipse = new Ellipse(tileSize * 0.3125, tileSize * 0.26);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(tileSize * 0.03);
        return ellipse;
    }

}
