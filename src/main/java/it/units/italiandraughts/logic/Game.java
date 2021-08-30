package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.ui.Drawer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {
    private Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private Status status;
    private Tile activeTile;
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

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<int[]> getLog() {
        return log;
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

    public void move(int fromX, int fromY, int toX, int toY, boolean shouldLog) {
        if ((toX + toY) % 2 == 1) {
            throw new IllegalMoveException("The required move is illegal because no piece can stand on a white tile");
        }
        Tile[][] tiles = getBoard().getTiles();
        Piece piece = tiles[fromY][fromX].getPiece();
        tiles[fromY][fromX].removePiece();
        tiles[toY][toX].placePiece(piece);
        if (shouldLog) {
            log.add(IntStream.of(fromX, fromY, toX, toY).toArray());
        }
        toggleActivePlayer();
    }

    public void reset() {
        board = new Board();
        log.clear();
        activePlayer = player1;
        status = Status.IDLE;
        support.removePropertyChangeListener(drawer);
        drawer = drawer.reset();
        addPropertyChangeListener(drawer);
    }

    public Tile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(Tile tile) {
        this.activeTile = tile;
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
        if (log.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button");
        }
        int[] coordinates = log.remove(log.size() - 1);
        move(coordinates[2], coordinates[3], coordinates[0], coordinates[1], false);
        drawer.updateBoard(board.getTiles());
        status = Status.IDLE;
    }
}