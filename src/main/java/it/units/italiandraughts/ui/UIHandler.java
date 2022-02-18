package it.units.italiandraughts.ui;

import it.units.italiandraughts.logic.Board;
import it.units.italiandraughts.logic.Game;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;

public abstract class UIHandler {

    protected final Square[][] squares = new Square[Board.SIZE][Board.SIZE];
    protected final Game game;
    private static List<UIHandler> instances = new LinkedList<>();

    UIHandler(Game game) {
        this.game = game;
        instances.add(this);
    }

    protected void handleClickOnSquare(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (square.isEmpty()) {
            instances.forEach(handler -> handler.onClickOnFullSquare);
        }
    }

}
