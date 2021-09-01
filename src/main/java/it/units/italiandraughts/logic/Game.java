package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.ui.Drawer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
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
        setInitialConditions();
        support = new PropertyChangeSupport(this);
        log = new ArrayList<>();
    }

    private void setInitialConditions() {
        activePlayer = player1;
        status = Status.IDLE;
        updateMovablePieces();
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
        updateMovablePieces();
        generateGraph();
    }

    private void updateMovablePieces() {
        Arrays.stream(board.getTiles()).flatMap(Arrays::stream)
                .filter(tile -> !tile.isEmpty() && tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor()))
                .forEach(this::setMovable);
    }

    private void setMovable(Tile tile) {
        boolean movable = getNeighbors(tile).stream()
                .anyMatch(neighbor -> canMove(neighbor) || canEat(tile, neighbor));
        tile.getPiece().setMovable(movable);
    }

    private List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int x = tile.getX();
        int y = tile.getY();
        List<Integer> deltas = new ArrayList<>();

        switch (tile.getPiece().getPieceType()) {
            case MAN -> deltas.add(activePlayer.equals(player1) ? 1 : -1);
            case KING -> Collections.addAll(deltas, -1, 1);
        }

        deltas.forEach(delta -> {
            if (isValidTile(x - 1, y - delta)) {
                neighbors.add(board.getTiles()[y - delta][x - 1]);
            }
            if (isValidTile(x + 1, y - delta)) {
                neighbors.add(board.getTiles()[y - delta][x + 1]);
            }
        });

        return neighbors;
    }

    private boolean canMove(Tile tile) {
        return tile.isEmpty();
    }

    private boolean canEat(Tile fromTile, Tile overTile) {
        if (!overTile.isEmpty() && !overTile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())) {
            int deltaX = overTile.getX() - fromTile.getX();
            int deltaY = overTile.getY() - fromTile.getY();
            int newX = overTile.getX() + deltaX;
            int newY = overTile.getY() + deltaY;
            return isValidTile(newX, newY) && canMove(board.getTiles()[newY][newX]);
        }
        return false;
    }

    private boolean isValidTile(int x, int y) {
        return (x < 8 && x >= 0) && (y < 8 && y >= 0);
    }

    public void reset() {
        board = new Board();
        log.clear();
        setInitialConditions();
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

    public void generateGraph() {

        Graph graph = new Graph(board);

        // TODO test how to add an edge
        graph.addEdge(board.getTiles()[0][0], board.getTiles()[0][2], 5);


    }
}