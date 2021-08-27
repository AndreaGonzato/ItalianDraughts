package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.ui.Drawer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {

    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private Status status;
    private Tile source;
    private Drawer drawer;
    private final PropertyChangeSupport support;
    private final List<int[]> log;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        activePlayer = player1;
        status = Status.IDLE;
        support = new PropertyChangeSupport(this);
        log = new ArrayList<>();
    }

    public Player getPlayer1() {
        return player1;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void toggleActivePlayer() {
        final Player oldActivePlayer = activePlayer;
        if (player1.equals(activePlayer)) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
        support.firePropertyChange("activePlayer", oldActivePlayer, activePlayer);
    }

    public void move(int fromX, int fromY, int toX, int toY) {
        if ((toX + toY) % 2 == 1) {
            throw new IllegalMoveException("The required move is illegal because no piece can stand on a white tile");
        }
        Tile[][] tiles = getBoard().getTiles();
        Piece piece = tiles[fromY][fromX].getPiece();
        tiles[fromY][fromX].placePiece(null);
        tiles[toY][toX].placePiece(piece);
        toggleActivePlayer();
        log.add(IntStream.of(fromX, fromY, toX, toY).toArray());
    }

    public void reset(){
        board.emptyPiecesFromTiles();
        board.placePieceInInitialPosition();
        drawer.updateBoard(board.getTiles());
    }

    public Tile getSource() {
        return source;
    }

    public void setSource(Tile tile) {
        this.source = tile;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public Status getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }

    public void undo() {
        // TODO implement this
    }
}